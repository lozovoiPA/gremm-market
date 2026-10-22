package com.implus.gremmarket.ui.deviceapps

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

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

class DeviceAppsViewState: ViewModel() {
    //private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    //private val ioScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    // Симуляция состояний загрузки
    var loadingState by mutableStateOf(DeviceAppsManagementLoadingStates.LOADING)
    // Это флаги фильтров. Контролируют включение/выключение кнопок фильтрации
    var appDeviceFilter by mutableStateOf(AppDeviceFilter.ON_DEVICE)
    var appFilters by mutableIntStateOf(0)
    // Контролируем состояние нижней шторки фильтров
    var showBottomSheet by mutableStateOf(false)

    var selectedApps by mutableStateOf(setOf<Int>())
        private set
    var activeDownloads by mutableStateOf<Map<Int, DownloadProgress>>(emptyMap())
        private set
    var shownApps by mutableStateOf<List<AppInfo>>(emptyList())
        private set

    val isAnyAppSelected: Boolean
        get() = selectedApps.isNotEmpty()

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
            val filteredApps = withContext(Dispatchers.Default) {
                if (appFilters == 0) {
                    sampleApps.filter { app ->
                        app.appliesTo(appDeviceFilter)
                    }
                } else {
                    AppFilters.entries
                        .filter { filter ->
                            appFilters.isFilterToggled(filter)
                        }
                        .flatMap { filter ->
                            sampleApps.filter { app ->
                                app.appliesTo(filter) &&
                                        app.appliesTo(appDeviceFilter)
                            }
                        }
                        .distinct()
                }
            }
            shownApps = filteredApps

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

                val filteredApps = withContext(Dispatchers.Default) {
                        sampleApps.filter { app ->
                            app.appliesTo(appFilters) && app.appliesTo(appDeviceFilter)
                        }
                            .distinct()
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

    fun startDownload(apps: List<AppInfo>) {
        selectedApps.forEach { appIndex ->
            val app = apps[appIndex]
            activeDownloads = activeDownloads + (appIndex to DownloadProgress())

            viewModelScope.launch {
                simulateAppDownload(appIndex, app.sizeBytes)
            }
        }
        selectedApps = emptySet()
    }

    private suspend fun simulateAppDownload(appId: Int, totalBytes: Long) {
        var currentProgress = .0f
        var currentState = DownloadState.RETRIEVAL
        val app = sampleApps[appId]

        while (currentState != DownloadState.FINISHED) {
            Log.d("GREM", "App \"${app.name}\" $currentState simulating with progress of $currentProgress")


            delay(currentState.getRandomDownloadValue().milliseconds)

            val chunk = currentState.getRandomDownloadChunk()
            currentProgress = (currentProgress + chunk).coerceAtMost(1f)

            activeDownloads = activeDownloads + (appId to DownloadProgress(
                progress = currentProgress,
                downloadState = currentState,
                downloadedBytes = (totalBytes * currentProgress).toLong(),
                totalBytes = totalBytes
            ))
            if(currentProgress >= 1f) {
                delay(200.milliseconds)
                currentState = currentState.advanceState()
                currentProgress = 0f
                activeDownloads = activeDownloads + (appId to DownloadProgress(
                    progress = 0f,
                    downloadState = currentState
                ))
            }
        }

        delay(1000.milliseconds)
        activeDownloads = activeDownloads - appId
        app.installed = true

        if(!(app.appliesTo(appFilters) && app.appliesTo(appDeviceFilter)) && app in shownApps) {
            shownApps = shownApps - app
        }
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
    var installed: Boolean = false,
    val iconBgColor: Color = Color.White,
    val sizeBytes: Long = 10_000_000L,
    var lastUpdated: String = ""
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