package com.implus.gremmarket.ui.deviceapps

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.DevicesOther
import androidx.compose.material.icons.outlined.Dns
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.PersonPin
import androidx.compose.material.icons.outlined.PlaylistRemove
import androidx.compose.material.icons.outlined.ShieldMoon
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogWindowProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import com.implus.gremmarket.DeviceAppsLocalNavigation
import com.implus.gremmarket.LocalNavigation
import com.implus.gremmarket.R
import com.implus.gremmarket.TextSecondary
import com.implus.gremmarket.ui.Destination
import com.implus.gremmarket.ui.MenuGroup
import com.implus.gremmarket.ui.MenuItemData
import com.implus.gremmarket.ui.MenuListItem
import com.implus.gremmarket.ui.deviceapps.applist.AppListTitle
import com.implus.gremmarket.ui.deviceapps.applist.AppRow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun DeviceAppsScreenScaffold(
    modifier: Modifier = Modifier,
    content: @Composable ((PaddingValues, Modifier) -> Unit) =
        { paddingValues, modifierInner -> DeviceAppsGeneralContent(paddingValues, modifierInner) }
) {
    val viewState = remember { DeviceAppsViewState() }

    CompositionLocalProvider(LocalDeviceAppsState provides viewState) {
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
                if (viewState.isAnyAppSelected) {
                    DeviceAppsSelectionManagementTopBar()
                } else {
                    DeviceAppsTopBar(modifier)
                }

            }
        ) { contentPadding ->
            val innerPadding = WindowInsets.safeDrawing.asPaddingValues()
                .calculateEndPadding(LocalLayoutDirection.current)

            val modifierInner = modifier
                .padding(
                    start = innerPadding,
                    end = innerPadding
                )

            content(contentPadding, modifierInner)
        }
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


    Column(modifier = Modifier) {
        DeviceAppsTopBarHeader(
            modifierInner,
            topBarColors
        )
        Column(modifier = Modifier
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

@Composable
fun DeviceAppsSelectionManagementTopBar() {
    val viewState = LocalDeviceAppsState.current
    val titleText = when (val selectedAppsCount = viewState.selectedApps.size) {
        1 -> "Выделено 1 приложение"
        2,3,4 -> "Выделено $selectedAppsCount приложения"
        else -> "Выделено $selectedAppsCount приложений"
    }

    val colors = TopAppBarDefaults.topAppBarColors().copy(
        containerColor = colorResource(R.color.MarketBackground)
    )

    Column(modifier = Modifier) {
        TopAppBar(
            colors = colors,
            title = {
                Text(
                    text = titleText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                ActionIcon(
                    onClick = { viewState.clearSelection() },
                    icon = Icons.Outlined.Close
                )
            },
            actions = {
                Row() {
                    if(viewState.appDeviceFilter == AppDeviceFilter.NOT_INSTALLED) {
                        ActionIcon(
                            onClick = {
                                viewState.startDownload(sampleApps)
                            },
                            icon = Icons.Outlined.Download
                        )
                    }
                    ActionIcon(
                        onClick = { },
                        icon = Icons.Outlined.Delete
                    )
                }
            }
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )
    }
}

@Composable
fun ActionIcon(
    onClick: () -> Unit = {},
    icon: ImageVector,
    modifier: Modifier = Modifier
        .size(48.dp)
        .padding(3.dp)
        .clip(CircleShape)
        .clickable(
            onClick = { onClick() },
            indication = null/* ripple(color = colorResource(R.color.ClickRippleGray)) */,
            interactionSource = remember { MutableInteractionSource() },
            role = Role.Button
        ),
    iconModifier: Modifier = Modifier
        .size(24.dp)
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = iconModifier
        )
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
            ActionIcon(
                onClick = { navController.navigate(Destination.HOME_GAMES.route) },
                icon = ImageVector.vectorResource(R.drawable.outline_arrow_back_24)
            )
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
@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DeviceAppsManagementContent(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val viewState = LocalDeviceAppsState.current

    /*
    // Симуляция состояний загрузки
    var loadingState by remember { mutableStateOf(DeviceAppsManagementLoadingStates.LOADING) }
    // Это сборный флаг всех возможных фильтров. Контролируют включение/выключение кнопок фильтрации
    var appDeviceFilter by remember { mutableStateOf(AppDeviceFilter.ON_DEVICE) }
    // Контролируем состояние нижней шторки фильтров
    var showBottomSheet by remember { mutableStateOf(false) }
     */
    val sheetState = rememberModalBottomSheetState()

    // попытки повторить анимацию фейда + слайда (последовательного) из гп
    // конечно как всегда нативно есть только одновременно, но нет последовательно......
    var fadeState = remember { MutableTransitionState(!viewState.isAnyAppSelected) }
    var slideState = remember { MutableTransitionState(false) }

    var fadeFirst by remember { mutableStateOf(true) }

    LaunchedEffect(viewState.isAnyAppSelected) {
        if (fadeFirst) {
            fadeState.targetState = !viewState.isAnyAppSelected
        } else {
            slideState.targetState = !viewState.isAnyAppSelected
        }
    }

    LaunchedEffect(slideState) {
        snapshotFlow { slideState.isIdle }
            .filter { it }
            .collect {
                fadeState.targetState = slideState.currentState
            }
        fadeFirst != fadeFirst
    }
    LaunchedEffect(fadeState) {
        snapshotFlow { fadeState.isIdle }
            .filter { it }
            .collect {
                slideState.targetState = fadeState.currentState
            }
        fadeFirst != fadeFirst
    }
    val scope = rememberCoroutineScope()

    // Симуляция загрузки экрана
    LaunchedEffect(Unit) {
        delay(1000.milliseconds)
        viewState.loadingState = DeviceAppsManagementLoadingStates.APPS_SHOWN
    }

    when(viewState.loadingState) {
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
            Column(
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
                AnimatedVisibility(
                    visible = !viewState.isAnyAppSelected,
                    enter = slideInVertically() + expandVertically(),
                    exit = slideOutVertically() + shrinkVertically()
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        AnimatedVisibility(
                            visible = !viewState.isAnyAppSelected,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Column() {
                                AppFiltersChipsRow(
                                    viewState.appDeviceFilter,
                                    { viewState.showBottomSheet = true },
                                    Modifier
                                        .padding(vertical = 5.dp)
                                )
                                AppListTitle(
                                    viewState.appDeviceFilter,
                                    Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .padding(start = 10.dp)
                                        .padding(top = 15.dp)
                                )
                            }
                        }
                    }
                }

                LazyColumn(
                    /*modifier = modifier
                        .fillMaxSize()
                        .padding(contentPadding) // Внешний паддинг вычисляемый скаффолдом - учитывает его TopBar и BottomBar
                        .consumeWindowInsets(contentPadding) // Паддинг выше устанавливается для всех вложенных элементов
                        .windowInsetsPadding( // Учитываем системные треи
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal
                            )
                        )*/
                    modifier = Modifier
                        .offset(y = 5.dp)
                ) {
                    stickyHeader {

                    }

                    item {
                        Column(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .padding(start = 4.dp)
                        ) {
                            sampleApps.forEach { app ->
                                val appIndex = sampleApps.indexOf(app)
                                AppRow(
                                    app = app, checked = appIndex in viewState.selectedApps,
                                    onChecked = { viewState.toggleSelection(appIndex) },
                                    viewState = viewState
                                )
                            }
                        }
                    }


                }

            }


            if (viewState.showBottomSheet) {
                AppDeviceFilterBottomSheet(
                    sheetState = sheetState,
                    appDeviceFilter = viewState.appDeviceFilter,
                    onClickDeviceFilter = {
                        if (viewState.appDeviceFilter != AppDeviceFilter.ON_DEVICE) viewState.appDeviceFilter = AppDeviceFilter.ON_DEVICE
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                viewState.showBottomSheet = false
                            }
                        }
                    },
                    onClickNotInstalledFilter = {
                        if (viewState.appDeviceFilter != AppDeviceFilter.NOT_INSTALLED) viewState.appDeviceFilter = AppDeviceFilter.NOT_INSTALLED
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                viewState.showBottomSheet = false
                            }
                        }
                    },
                    onDismissRequest = { viewState.showBottomSheet = false }
                )
            }
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
                    containerColor = colorResource(R.color.MarketAccentBlue),
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
                            MenuItemData(
                                Icons.Outlined.Smartphone,
                                "Это устройство"
                            ),
                            colors = if (appDeviceFilter == AppDeviceFilter.ON_DEVICE) selectedItemColors else itemColors,
                            onClick = onClickDeviceFilter
                        )
                    },
                    {
                        MenuListItem(
                            MenuItemData(
                                Icons.Outlined.PlaylistRemove,
                                "Не установленные"
                            ),
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
        selectedContainerColor = colorResource(R.color.MarketAccentBlue),
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