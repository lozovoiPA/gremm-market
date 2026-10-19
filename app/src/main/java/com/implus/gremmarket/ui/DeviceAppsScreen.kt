package com.implus.gremmarket.ui

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.DevicesOther
import androidx.compose.material.icons.outlined.Dns
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.PersonPin
import androidx.compose.material.icons.outlined.PlaylistRemove
import androidx.compose.material.icons.outlined.ShieldMoon
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogWindowProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import com.implus.gremmarket.DeviceAppsLocalNavigation
import com.implus.gremmarket.LocalNavigation
import com.implus.gremmarket.R
import com.implus.gremmarket.TextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun DeviceAppsScreenScaffold(
    modifier: Modifier = Modifier,
    content: @Composable ((PaddingValues, Modifier) -> Unit) =
        { paddingValues, modifierInner -> DeviceAppsGeneralContent(paddingValues, modifierInner) }
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = colorResource(R.color.MarketBackground),
        topBar = {
            /*
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding())
                    .background(Color.White)
            ) {} */
            DeviceAppsTopBar(modifier)
        }
    ) {
        contentPadding ->
        val innerPadding = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(LocalLayoutDirection.current)

        val modifierInner = modifier
            .padding(
                start = innerPadding,
                end = innerPadding
            )

        content(contentPadding, modifierInner)
    }
}

@Composable
fun DeviceAppsTopBar(
    modifier: Modifier
) {
    // Задаем параметры для элементов топ бара (состоит из "заголовка", вкладок и разделителя)
    // Разделитель отдельно поскольку у него нет дополнительного паддинга (см. modifierInner)
    val modifierInner = modifier
        .padding(
            horizontal = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(LocalLayoutDirection.current)
        )
    val topBarColors = TopAppBarDefaults.topAppBarColors().copy(
        containerColor = colorResource(R.color.MarketBackground)
    )

    Column(
        modifier = Modifier
    ) {
        DeviceAppsTopBarHeader(
            modifierInner,
            topBarColors
        )
        Column(
            modifier = Modifier
                .offset(y = (-10).dp)
        ) {
            DeviceAppsTopBarTabs(
                modifier = modifierInner
            )
            HorizontalDivider(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceAppsTopBarHeader(
    modifier: Modifier,
    colors: TopAppBarColors
) {
    val navController = LocalNavigation.current
    TopAppBar(
        modifier = modifier,
        colors = colors,
        title = {
            Text(
                text = "Управление устройством и приложениями",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .padding(3.dp)
                    .clip(CircleShape)
                    .clickable(
                        onClick = { navController.navigate(Destination.HOME_GAMES.route) },
                        indication = ripple(color = colorResource(R.color.ClickRippleGray)),
                        interactionSource = remember { MutableInteractionSource() },
                        role = Role.Button
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.outline_arrow_back_24),
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )
}

@Composable
fun DeviceAppsTopBarTabs(
    modifier: Modifier
) {
    val navController = DeviceAppsLocalNavigation.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route


    var selectedTab by rememberSaveable {
        mutableIntStateOf(
            when(currentRoute) {
                Destination.DEVICEAPPS_GENERAL.route -> 0
                Destination.DEVICEAPPS_APPS.route -> 1
                else -> 0
            }
        )
    }
    val tabs = listOf(Destination.DEVICEAPPS_GENERAL, Destination.DEVICEAPPS_APPS)

    PrimaryScrollableTabRow(
        selectedTab,
        modifier = modifier,
        containerColor = Color.White,
        contentColor = colorResource(R.color.MarketBlue),
        edgePadding = 0.dp,
        tabs = {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTab == index,
                    modifier = Modifier,
                    onClick = {
                        navController.navigate(tab.route)
                        selectedTab = index
                    },
                    text = {
                        Text(
                            text = tab.label,
                            fontWeight = FontWeight.Normal,
                            color = if (selectedTab == index) colorResource(R.color.MarketBlue) else TextSecondary
                        )
                    }
                )
            }
        },
        divider = {},
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(selectedTab, matchContentSize = true)
                    .offset(y=1.dp),
                width = Dp.Unspecified,
                height = 4.dp,
                color = colorResource(R.color.MarketBlue),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            )
        }
    )
}

enum class DeviceAppsManagementLoadingStates() {
    LOADING,
    APPS_LOADING,
    APPS_SHOWN
}

enum class AppDeviceFilter(
    val label: String
) {
    ON_DEVICE("Это устройство"),
    NOT_INSTALLED("Не установленные")
}

enum class AppFilters(
    val label: String = ""
) {
    RECENT_UPDATES("Доступны обновления"),
    GAMES("Игры"),
    ARCHIVED("Архивированные")
}

data class AppInfo(
    val name: String,
    val size: String,
    val icon: String,
    val game: Boolean = false,
    val installed: Boolean = false,
    val selected: Boolean = false,
    val progress: Int = 0,
    val iconBgColor: Color = Color.White
)

val sampleApps = listOf(
    AppInfo("1.1.1.1 Three Letters", "14 МБ", "🌐"),
    AppInfo("3.14ГИС: карты, навигатор, мат.анализ", "141 МБ", "🗺️"),
    AppInfo("AddClean for Your Browser", "7,5 МБ", "🛡️"),
    AppInfo("Adowas Photohop Sketch", "71 МБ", "🖌️"),
    AppInfo("OTK Arena", "424 МБ", "🌄", true),
    AppInfo("EarthDroid: файлы и управление папками", "54 МБ", "📡"),
    AppInfo("EarthMirror: контроль и поддержка приложений", "42 МБ", "🪞"),
    AppInfo("Alzheimer NPV", "64 МБ", "🔒"),
    AppInfo("Barkadium - Космический шутер", "48,6 МБ", "👾", true),
    AppInfo("Bunbun Cakery", "62 МБ", "🧁", true),
    AppInfo("BRICKSITY - Sandbox&Multiplayer game", "338 МБ", "🧩", true),
    AppInfo("YellowDust0.12 - Full Burst RPG", "279 МБ", "🎭", true),
    AppInfo("Bulew Monstaw", "120 МБ", "🐲", true),
    AppInfo("Bus IN: Припаркуй автобус", "105 МБ", "🚌", true),
    AppInfo("Candy Sweet Legend", "79 МБ", "🍬", true),
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DeviceAppsManagementContent(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    // Симуляция состояний загрузки
    var loadingState by remember { mutableStateOf(DeviceAppsManagementLoadingStates.LOADING) }
    // Это сборный флаг всех возможных фильтров. Контролируют включение/выключение кнопок фильтрации
    var appDeviceFilter by remember { mutableStateOf(AppDeviceFilter.ON_DEVICE) }
    // Контролируем состояние нижней шторки фильтров
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    // Симуляция загрузки экрана
    LaunchedEffect(Unit) {
        delay(1000.milliseconds)
        loadingState = DeviceAppsManagementLoadingStates.APPS_SHOWN
    }

    when(loadingState) {
        DeviceAppsManagementLoadingStates.LOADING -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding) // Внешний паддинг вычисляемый скаффолдом - учитывает его TopBar и BottomBar
                    .consumeWindowInsets(contentPadding) // Паддинг выше устанавливается для всех вложенных элементов
                    .windowInsetsPadding( // Учитываем системные треи
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal
                        )
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingIndicator(color = colorResource(R.color.MarketBlue))
            }

        }

        else -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(contentPadding) // Внешний паддинг вычисляемый скаффолдом - учитывает его TopBar и BottomBar
                    .consumeWindowInsets(contentPadding) // Паддинг выше устанавливается для всех вложенных элементов
                    .windowInsetsPadding( // Учитываем системные треи
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal
                        )
                    )
            ) {
                stickyHeader {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        AppFiltersChipsRow(
                            appDeviceFilter,
                            { showBottomSheet = true },
                            Modifier
                                .padding(vertical = 5.dp)
                        )
                        AppListTitle(
                            appDeviceFilter,
                            Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(start = 10.dp)
                                .padding(top = 15.dp)
                        )
                    }
                }
                item {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        sampleApps.forEach { app ->
                            AppRow(
                                app = app, checked = app.selected,
                                onChecked = { },
                                progress = 0
                            )
                        }
                    }
                }


            }

            if (showBottomSheet) {
                AppDeviceFilterBottomSheet(
                    sheetState = sheetState,
                    appDeviceFilter = appDeviceFilter,
                    onClickDeviceFilter = {
                        if (appDeviceFilter != AppDeviceFilter.ON_DEVICE) appDeviceFilter = AppDeviceFilter.ON_DEVICE
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    },
                    onClickNotInstalledFilter = {
                        if (appDeviceFilter != AppDeviceFilter.NOT_INSTALLED) appDeviceFilter = AppDeviceFilter.NOT_INSTALLED
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    },
                    onDismissRequest = { showBottomSheet = false }
                )
            }
        }
    }
}

@Composable
private fun AppRow(
    app: AppInfo,
    checked: Boolean,
    onChecked: (Boolean) -> Unit,
    progress: Int = 0
) {
    Row(
        Modifier
            .fillMaxWidth()
            .heightIn(min = 46.dp)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(42.dp)
                .dropShadow(
                    shape = RoundedCornerShape(14.dp),
                    shadow = Shadow(
                        radius = 2.dp,
                        spread = 0.dp,
                        color = Color.LightGray,
                        offset = DpOffset(x = (-1).dp, 2.dp)
                    )
                )
                .background(color = app.iconBgColor, shape = RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(app.icon, fontSize = 21.sp)
        }

        Spacer(Modifier.width(9.dp))
        Column(Modifier.weight(1f)) {
            Text(app.name, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            if (progress > 0) {
                Spacer(Modifier.height(2.dp))
                LinearProgressIndicator(progress = { progress / 100f }, modifier = Modifier.fillMaxWidth().height(3.dp))
                Text("$progress% из ${app.size}", fontSize = 9.sp)
            } else {
                Text("${app.size}  •  Обновлено ${if (app.installed) "вчера" else "2 дня назад"}", fontSize = 9.sp, maxLines = 1)
            }
        }
        Icon(Icons.Default.ExpandMore, null, modifier = Modifier.size(20.dp))
        Checkbox(
            checked = checked, onCheckedChange = onChecked,
            modifier = Modifier.size(34.dp), colors = CheckboxDefaults.colors()
        )
    }
}

@Composable
fun AppListTitle(
    appDeviceFilter: AppDeviceFilter,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            if (appDeviceFilter == AppDeviceFilter.ON_DEVICE) "Игры и приложения" else "Приложения",
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth(0.4f),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            fontSize = 18.sp
        )
        Row(
            modifier = Modifier
                .requiredWidth(200.dp)
                .fillMaxHeight()
                .clickable(
                    onClick = { },
                    indication = ripple(color = colorResource(R.color.ClickRippleGray)), // Анимация зеленого эффекта по нажатию. Судя по докам должна быть по дефолту из М3, но почему-то у меня нет
                    interactionSource = remember { MutableInteractionSource() },
                    role = Role.Button
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Недавно обновленные",
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.width(7.dp))
            Icon(
                imageVector = Icons.Outlined.Sort,
                contentDescription = null,
                modifier = Modifier
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDeviceFilterBottomSheet(
    sheetState: SheetState,
    appDeviceFilter: AppDeviceFilter,
    onClickDeviceFilter: () -> Unit = {},
    onClickNotInstalledFilter: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        sheetState = sheetState,

        // Затемнение экрана
        scrimColor = Color.Black.copy(alpha = 0.5f),
        containerColor = colorResource(R.color.MarketBackground),
        // Убираем дефолтную ручку перетаскивания
        dragHandle = {},
        shape = RectangleShape
    ) {
        // Делаем бар навигации прозрачным (этот модал считается отдельным окном)
        val view = LocalView.current
        val dialogWindow = (view.parent as? DialogWindowProvider)?.window

        SideEffect {
            dialogWindow?.let { window ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    window.isNavigationBarContrastEnforced = false
                }
                window.navigationBarColor = Color.Transparent.toArgb()
            }
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .navigationBarsPadding()
        ) {
            Text(
                "Выберите устройство",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                letterSpacing = 0.sp,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 10.dp)
            ) {
                val selectedItemColors = ListItemDefaults.colors().copy(
                    containerColor = colorResource(R.color.FilterChipBlueContainer),
                    headlineColor = Color.Black,
                    leadingIconColor = Color.DarkGray
                )
                val itemColors = ListItemDefaults.colors().copy(
                    containerColor = Color.Transparent,
                    headlineColor = Color.DarkGray,
                    leadingIconColor = Color.DarkGray
                )

                MenuGroup(
                    {
                        MenuListItem(
                            MenuItemData(Icons.Outlined.Smartphone, "Это устройство"),
                            colors = if (appDeviceFilter == AppDeviceFilter.ON_DEVICE) selectedItemColors else itemColors,
                            onClick = onClickDeviceFilter
                        )
                    },
                    {
                        MenuListItem(
                            MenuItemData(Icons.Outlined.PlaylistRemove, "Не установленные"),
                            colors = if (appDeviceFilter == AppDeviceFilter.NOT_INSTALLED) selectedItemColors else itemColors,
                            onClick = onClickNotInstalledFilter
                        )
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp)),
                    menuDivider = {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color.LightGray
                        )
                    }
                )
            }

        }
    }
}

@Composable
fun AppFiltersChipsRow(
    appDeviceFilter: AppDeviceFilter,
    onAppDeviceFilterChipClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var appFiltersStates by remember { mutableIntStateOf(0) }

    val filterChipColors = FilterChipDefaults.filterChipColors().copy(
        selectedContainerColor = colorResource(R.color.FilterChipBlueContainer),
        selectedLabelColor = colorResource(R.color.FilterChipBlueText),
        selectedLeadingIconColor = colorResource(R.color.FilterChipBlueText),
        selectedTrailingIconColor = colorResource(R.color.FilterChipBlueText)
    )

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                modifier = Modifier
                    .padding(start = 10.dp),
                selected = true,
                onClick = { onAppDeviceFilterChipClick() },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = null,
                        modifier = Modifier
                            .size(15.dp)
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                    )
                },
                label = { Text(appDeviceFilter.label) },
                colors = filterChipColors
            )
        }

        val appFilters = if (appDeviceFilter == AppDeviceFilter.ON_DEVICE) AppFilters.entries else listOf(AppFilters.GAMES)
        appFilters.forEachIndexed { index, filter ->
            item {
                val filterBitFlag = (1 shl filter.ordinal)
                val selected = (appFiltersStates and filterBitFlag) != 0 // проверка включен ли фильтр (номер в enum определяет бит флага)
                val chipModifier =
                    if (index == AppFilters.entries.lastIndex)
                        Modifier.padding(end = 10.dp)
                    else
                        Modifier

                FilterChip(
                    modifier = chipModifier,
                    selected = selected,
                    onClick = {
                        appFiltersStates = appFiltersStates xor filterBitFlag
                    },
                    leadingIcon = {
                        if (selected) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(15.dp),
                            )
                        }
                        else { null }
                    },
                    label = { Text(filter.label) },
                    colors = filterChipColors
                )
            }
        }
    }
}

@Composable
fun DeviceAppsGeneralContent(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier // Модификатор для паддинга с учетом TopBar, BottomBar, и системных треев.
            .fillMaxSize()
            .padding(contentPadding) // Внешний паддинг вычисляемый скаффолдом - учитывает его TopBar и BottomBar
            .consumeWindowInsets(contentPadding) // Паддинг выше устанавливается для всех вложенных элементов
            .windowInsetsPadding( // Учитываем системные треи
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal
                )
            )
    )
    {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp)
        ) {
            RowMenuItem(
                MenuItemData(
                    icon = Icons.Outlined.ShieldMoon,
                    "Вредоносные приложения не найдены",
                    "Проверка выполнена вчера"
                )
            )
            RowMenuItem(
                MenuItemData(
                    icon = Icons.Outlined.GridView,
                    "Доступны обновления",
                    "6 обновлений ожидают скачивания.",
                    trailingContent = {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Обновить все",
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .clickable(
                                        onClick = {},
                                        indication = ripple(color = colorResource(R.color.ClickRippleGreen)),
                                        interactionSource = remember { MutableInteractionSource() },
                                        role = Role.Button
                                    )
                                    .padding(vertical = 12.dp),
                                color = colorResource(R.color.ClickRippleGreen),
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = "Подробнее",
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .clickable(
                                        onClick = {},
                                        indication = ripple(color = colorResource(R.color.ClickRippleGreen)),
                                        interactionSource = remember { MutableInteractionSource() },
                                        role = Role.Button
                                    )
                                    .padding(vertical = 12.dp),
                                color = colorResource(R.color.ClickRippleGreen),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                )
            )
            RowMenuItem(
                MenuItemData(
                    icon = Icons.Outlined.Dns,
                    "71 ГБ из 115 ГБ"
                )
            )
            RowMenuItem(
                MenuItemData(
                    icon = Icons.Outlined.DevicesOther,
                    "Синхронизация приложений с устройствами",
                    "Синхронизированных устройств нет."
                )
            )
            RowMenuItem(
                MenuItemData(
                    icon = Icons.Outlined.Stars,
                    "Оценки и отзывы"
                )
            )
            RowMenuItem(
                MenuItemData(
                    icon = Icons.Outlined.PersonPin,
                    "Ваши впечатления"
                )
            )
        }
    }
}

@Composable
fun RowMenuItem(
    itemData: MenuItemData,
    onClick: () -> Unit = {}
) {
    val titleStyle = MaterialTheme.typography.bodyLarge
    val subtitleStyle = MaterialTheme.typography.bodyMedium

    Layout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                role = Role.Button
            )
            .padding(vertical = 16.dp),
        content = {
            Icon(
                imageVector = itemData.icon,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )

            Column {
                Text(
                    text = itemData.title,
                    style = titleStyle,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (itemData.subtitle != null) {
                    Text(
                        text = itemData.subtitle,
                        style = subtitleStyle,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                itemData.trailingContent?.invoke()
            }

            if (itemData.trailingText != null) {
                Text(
                    text = itemData.trailingText,
                    style = subtitleStyle,
                    color = Color.Gray
                )
            }
        }
    ) { measurables, constraints ->
        // --- MEASUREMENT PHASE ---

        val totalWidth = constraints.maxWidth
        val iconMeasurable = measurables[0]
        val trailingMeasurable = measurables.getOrNull(2)

        // 1. Measure Icon and Trailing FIRST to know how much width they take
        val iconPlaceable = iconMeasurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
        val trailingPlaceable = trailingMeasurable?.measure(constraints.copy(minWidth = 0, minHeight = 0))

        val iconWidth = iconPlaceable.width
        val trailingWidth = trailingPlaceable?.width ?: 0
        val spacing = 32.dp.roundToPx()

        // 2. Calculate available width for the Text Column
        val columnMaxWidth = totalWidth - iconWidth - spacing - trailingWidth

        // 3. Measure the Column with the CORRECT max width constraint
        val columnMeasurable = measurables[1]
        val columnPlaceable = columnMeasurable.measure(
            constraints.copy(
                minWidth = 0,
                minHeight = 0,
                maxWidth = columnMaxWidth // <--- THIS PREVENTS THE OVERFLOW
            )
        )

        // 4. Calculate Title Height for icon alignment
        val titleLineHeightPx = with(titleStyle) {
            val lineHeightSp = if (lineHeight.value > 0f) lineHeight.value else fontSize.value
            (lineHeightSp * 2).sp
        }.toPx()

        // --- PLACEMENT PHASE ---
        val rowHeight = maxOf(
            columnPlaceable.height,
            iconPlaceable.height,
            trailingPlaceable?.height ?: 0
        )

        layout(totalWidth, rowHeight) {
            // 1. Place Icon (centered on first 2 lines of text)
            val iconCenterY = if (columnPlaceable.height < titleLineHeightPx) {
                columnPlaceable.height / 2
            } else {
                titleLineHeightPx / 2
            }
            val iconY = iconCenterY.toInt() - (iconPlaceable.height / 2)
            iconPlaceable.placeRelative(x = 0, y = iconY)

            // 2. Place Column
            columnPlaceable.placeRelative(x = iconWidth + spacing, y = 0)

            // 3. Place Trailing Text (right-aligned, vertically centered)
            if (trailingPlaceable != null) {
                val trailingY = (rowHeight - trailingPlaceable.height) / 2
                trailingPlaceable.placeRelative(
                    x = totalWidth - trailingPlaceable.width,
                    y = trailingY
                )
            }
        }
    }
}

@Composable
fun RowMenuItem2(
    itemData: MenuItemData
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {},
                indication = ripple(color = colorResource(R.color.ClickRippleGray)),
                interactionSource = remember { MutableInteractionSource() },
                role = Role.Button
            )
            .padding(vertical = 16.dp)
    ) {
        Icon(
            imageVector = itemData.icon,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier
                .size(24.dp)
        )
        Spacer(Modifier.width(32.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(itemData.title, style = MaterialTheme.typography.bodyLarge, maxLines = 2, overflow = TextOverflow.Ellipsis)
            if (itemData.subtitle != null) {
                Text(itemData.subtitle, style = MaterialTheme.typography.bodyMedium, color = TextSecondary, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            itemData.trailingContent?.invoke()
        }

        if (itemData.trailingText != null) {
            Text(itemData.trailingText, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
        }
    }
}