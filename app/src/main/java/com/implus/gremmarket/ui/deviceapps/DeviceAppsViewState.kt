package com.implus.gremmarket.ui.deviceapps

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import kotlin.random.Random

enum class DeviceAppsManagementLoadingStates() {
    LOADING,
    APPS_LOADING,
    APPS_SHOWN
}

enum class AppDeviceFilter(
    val label: String
) {
    ON_DEVICE("Это устройство"),
    NOT_INSTALLED("Не установленные");

    fun toggle(): AppDeviceFilter {
        return if (this != ON_DEVICE) ON_DEVICE else NOT_INSTALLED
    }
}

enum class AppFilters(
    val label: String = "",
) {
    HAS_UPDATES("Доступны обновления"),
    GAMES("Игры"),
    ARCHIVED("Архивированные");

    val flagBit: Int
        get() = (1 shl this.ordinal)
}

fun AppInfo.appliesTo(filter: AppFilters): Boolean {
    when(filter) {
        AppFilters.GAMES -> if (!this.game) return false
        AppFilters.HAS_UPDATES -> if(this.lastUpdated.isNotEmpty()) return false
        AppFilters.ARCHIVED -> return false
    }
    return true
}

fun AppInfo.appliesTo(deviceFilter: AppDeviceFilter): Boolean {
    when(deviceFilter) {
        AppDeviceFilter.ON_DEVICE -> if (!this.installed) return false
        AppDeviceFilter.NOT_INSTALLED -> if (this.installed) return false
    }
    return true
}

fun AppInfo.appliesTo(flags: Int): Boolean {
    AppFilters.entries.forEach { x ->
        if (flags.isFilterToggled(x) && !this.appliesTo(x)) return false
    }
    return true
}

fun Int.isFilterToggled(flag: AppFilters): Boolean {
    return ((this and flag.flagBit) != 0)
}

fun Int.toggleFilter(flag: AppFilters): Int {
    return (this xor flag.flagBit)
}

class DeviceAppsViewState(
    private val appsRepository: AppsRepository
): ViewModel() {
    // Симуляция состояний загрузки
    var loadingState by mutableStateOf(DeviceAppsManagementLoadingStates.LOADING)
    // Это флаги фильтров. Контролируют включение/выключение кнопок фильтрации
    var appDeviceFilter by mutableStateOf(AppDeviceFilter.ON_DEVICE)
    var appFilters by mutableIntStateOf(0)
    // Контролируем состояние нижней шторки фильтров
    var showBottomSheet by mutableStateOf(false)

    var selectedApps by mutableStateOf(setOf<Int>())
        private set
    // Индексы отображаемых приложений из apps
    var shownApps by mutableStateOf<Set<Int>>(emptySet())
        private set

    // Приложения и загрузки хранятся в репозитории и переживают уход с экрана
    val apps: List<AppInfo>
        get() = appsRepository.apps
    val activeDownloads: Map<Int, DownloadProgress>
        get() = appsRepository.activeDownloads

    var selectedTab by mutableIntStateOf(0)

    val isAnyAppSelected: Boolean
        get() = selectedApps.isNotEmpty()

    init {
        // Убираем из списка приложения, переставшие подходить под фильтры (например, после установки)
        viewModelScope.launch {
            snapshotFlow { appsRepository.apps }
                .collect { apps ->
                    shownApps = shownApps.filterTo(mutableSetOf()) { appId ->
                        apps[appId].appliesTo(appFilters) && apps[appId].appliesTo(appDeviceFilter)
                    }
                }
        }
    }

    fun toggleSelection(appId: Int) {
        selectedApps = if (selectedApps.contains(appId)) {
            selectedApps - appId
        } else {
            selectedApps + appId
        }
    }

    fun queryShownAppsByFilter() {
        queryShownAppsByFilter(appDeviceFilter, appFilters)
    }

    suspend fun queryShownAppsByFilterWithNoStateChange() {
        try {
            val apps = appsRepository.apps
            val filteredApps = withContext(Dispatchers.Default) {
                if (appFilters == 0) {
                    apps.indices.filter { appId ->
                        apps[appId].appliesTo(appDeviceFilter)
                    }
                } else {
                    AppFilters.entries
                        .filter { filter ->
                            appFilters.isFilterToggled(filter)
                        }
                        .flatMap { filter ->
                            apps.indices.filter { appId ->
                                apps[appId].appliesTo(filter) &&
                                        apps[appId].appliesTo(appDeviceFilter)
                            }
                        }
                }
            }
            shownApps = filteredApps.toSet()

        } finally {
            Log.d("GREM", "Filtering finished")
        }
    }

    // по установлено/неустановлено и по общему флагу
    fun queryShownAppsByFilter(appDeviceFilter: AppDeviceFilter, appFilters: Int) {
        viewModelScope.launch {
            try {
                if(loadingState != DeviceAppsManagementLoadingStates.LOADING) {
                    loadingState = DeviceAppsManagementLoadingStates.APPS_LOADING
                    yield()
                }

                val apps = appsRepository.apps
                val filteredApps = withContext(Dispatchers.Default) {
                    apps.indices.filter { appId ->
                        apps[appId].appliesTo(appFilters) && apps[appId].appliesTo(appDeviceFilter)
                    }.toSet()
                }
                shownApps = filteredApps

            } finally {
                if(loadingState == DeviceAppsManagementLoadingStates.APPS_LOADING)
                    loadingState = DeviceAppsManagementLoadingStates.APPS_SHOWN
                Log.d("GREM", "Filtering finished")
            }
        }
    }

    fun toggleAppDeviceFilter() {
        appDeviceFilter = appDeviceFilter.toggle()
        //queryShownAppsByFilter(appDeviceFilter, appFilters)
    }

    fun startDownload() {
        // Загрузка идет в скоупе приложения и не прерывается при уходе с экрана
        appsRepository.startDownload(selectedApps)
        selectedApps = emptySet()
    }

    fun clearSelection() {
        selectedApps = emptySet()
    }
}

data class DownloadProgress(
    val progress: Float = 0f,
    val timeTaken: Float = 0f,
    val downloadedBytes: Long = 0L,
    val downloadState: DownloadState = DownloadState.RETRIEVAL,
    val totalBytes: Long = 10_000_000L
)

enum class DownloadState(
    val label: String
) {
    RETRIEVAL("retrieval"),
    DOWNLOAD("download"),
    INSTALLING("installing"),
    FINISHED("finished"),
    STOPPED("stopped");

    fun advanceState(): DownloadState {
        when(this) {
            RETRIEVAL -> return DOWNLOAD
            DOWNLOAD -> return INSTALLING
            INSTALLING -> return FINISHED
            else -> RETRIEVAL
        }
        return RETRIEVAL
    }
    fun getRandomDownloadValue(): Long {
        val res: Long = when(this) {
            RETRIEVAL -> Random.nextLong(400, 800)
            DOWNLOAD -> Random.nextLong(1000, 1600)
            INSTALLING -> Random.nextLong(400, 800)
            else -> Random.nextLong(400, 1200)
        }
        return res
    }
    fun getRandomDownloadChunk(): Float {
        val res: Float = when(this) {
            RETRIEVAL -> Random.nextFloat() * 0.25f + 0.05f
            DOWNLOAD -> Random.nextFloat() * 0.05f + 0.05f
            INSTALLING -> Random.nextFloat() * 0.25f + 0.05f
            else -> Random.nextFloat() * 0.05f + 0.05f
        }
        return res
    }
}

data class AppInfo(
    val name: String,
    val size: String,
    val icon: String,
    val game: Boolean = false,
    val installed: Boolean = false,
    val iconBgColor: Color = Color.White,
    val sizeBytes: Long = 10_000_000L,
    val lastUpdated: String = "",
    val iconImageId: Int = 0,
    val bannerImageId: Int = 0,
    val rating: String = "",
    val genre: String = "",
    val subgenre: String = "",
    val price: String = "",
    val shortDescription: String = ""
)

val sampleApps = listOf(
    AppInfo("1.1.1.1 Three Letters", "14 МБ", "🌐", lastUpdated = "в 12:30"),
    AppInfo("3.14ГИС: карты, навигатор, мат.анализ", "141 МБ", "🗺️", lastUpdated = "в 13:38"),
    AppInfo("AddClean for Your Browser", "7,5 МБ", "🛡️", lastUpdated = "вчера"),
    AppInfo("Adowas Photohop Sketch", "71 МБ", "🖌️", lastUpdated = "вчера"),
    AppInfo("OTK Arena", "424 МБ", "🌄", true),
    AppInfo("EarthDroid: файлы и управление папками", "54 МБ", "📡", lastUpdated = "2 дня назад"),
    AppInfo("EarthMirror: контроль и поддержка приложений", "42 МБ", "🪞"),
    AppInfo("Alzheimer NPV", "64 МБ", "🔒"),
    AppInfo("Barkadium - Космический шутер", "48,6 МБ", "👾", true),
    AppInfo("Bunbun Cakery", "62 МБ", "🧁", true),
    AppInfo("BRICKSITY - Sandbox&Multiplayer game", "338 МБ", "🧩", true),
    AppInfo("YellowDust0.12 - Full Burst RPG", "279 МБ", "🎭", true),
    AppInfo("Bulew Monstaw", "120 МБ", "🐲", true),
    AppInfo("Bus IN: Припаркуй автобус", "105 МБ", "🚌", true, lastUpdated = "13 окт."),
    AppInfo("Candy Sweet Legend", "79 МБ", "🍬", true, lastUpdated = "1 сент."),
    AppInfo("Cats Residence: Игра про котиков", "327 МБ", "🐱", true),
    AppInfo("Chess.omw - Do you miss playing chess?", "102 МБ", "♟️", true),
    AppInfo("Grem Chrome", "2,9 ГБ", "🌐", installed = true),
    AppInfo("Grem Презентации", "253 МБ", "🟨", installed = true),
    AppInfo("Grmail", "236 МБ", "✉️", installed = true),
    AppInfo("Resource 4 Photo Widget", "57 МБ", "🟢", installed = true),
    AppInfo("Мяундекс Клавиатура с Алиной", "398 МБ", "Мяу", installed = true),
    AppInfo("Лед NPV", "107 МБ", "🔥", installed = true),
    AppInfo("Y (Ранее Wtiwwer)", "1,91 ГБ", "Y", installed = true)
)

val LocalDeviceAppsState = staticCompositionLocalOf<DeviceAppsViewState> {
    error("DeviceAppsViewState not provided")
}