package com.implus.gremmarket.ui.deviceapps.content

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.R
import com.implus.gremmarket.ui.deviceapps.DeviceAppsManagementLoadingStates
import com.implus.gremmarket.ui.deviceapps.LocalDeviceAppsState
import com.implus.gremmarket.ui.deviceapps.applist.AppFiltersChipsRow
import com.implus.gremmarket.ui.deviceapps.applist.AppListTitle
import com.implus.gremmarket.ui.deviceapps.applist.AppRow
import com.implus.gremmarket.ui.deviceapps.applist.AppSampleRow
import com.implus.gremmarket.ui.deviceapps.sampleApps
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DeviceAppsManagementContent(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val viewState = LocalDeviceAppsState.current
    val shownApps = viewState.shownApps

    /*
    // Симуляция состояний загрузки
    var loadingState by remember { mutableStateOf(DeviceAppsManagementLoadingStates.LOADING) }
    // Это сборный флаг всех возможных фильтров. Контролируют включение/выключение кнопок фильтрации
    var appDeviceFilter by remember { mutableStateOf(AppDeviceFilter.ON_DEVICE) }
    // Контролируем состояние нижней шторки фильтров
    var showBottomSheet by remember { mutableStateOf(false) }
     */

    // попытки повторить анимацию фейда + слайда (последовательного) из гп
    // конечно как всегда нативно есть только одновременно, но нет последовательно......
    var fadeState = remember { MutableTransitionState(!viewState.isAnyAppSelected) }
    var slideState = remember { MutableTransitionState(false) }

    var fadeFirst by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()

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

        DeviceAppsManagementLoadingStates.APPS_SHOWN -> {
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
                                    viewState,
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
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    sampleApps.forEach { app ->
                        val appIndex = sampleApps.indexOf(app)
                        val visible = app in shownApps
                        item {
                            Log.d("GREM", "Отображать?: $visible")
                            AnimatedVisibility(
                                visible = visible,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                AppRow(
                                    app = app, checked = appIndex in viewState.selectedApps,
                                    onChecked = { viewState.toggleSelection(appIndex) },
                                    viewState = viewState
                                )
                            }

                        }
                    }
                    /*
                    item {
                        Column(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .padding(start = 4.dp)
                        ) {

                        }
                    }
                     */


                }

            }
        }
        DeviceAppsManagementLoadingStates.APPS_LOADING -> {
            Log.d("GREM", "Apps are loading, samples should be shown")
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
                Column(
                    modifier = Modifier
                        .background(Color.White)
                ) {
                    Column() {
                        AppFiltersChipsRow(
                            viewState,
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

                Column(modifier = Modifier.offset(y = 5.dp)) {
                    Spacer(modifier = Modifier.height(10.dp))

                    for (i in 0..10) {
                        AppSampleRow()
                    }
                }
            }
        }
    }
}