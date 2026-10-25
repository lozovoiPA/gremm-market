package com.implus.gremmarket.ui.deviceapps

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.implus.gremmarket.GremApplication
import com.implus.gremmarket.R
import com.implus.gremmarket.ui.deviceapps.content.AppDeviceFilterBottomSheet
import com.implus.gremmarket.ui.deviceapps.content.DeviceAppsGeneralContent
import com.implus.gremmarket.ui.deviceapps.topbar.DeviceAppsSelectionManagementTopBar
import com.implus.gremmarket.ui.deviceapps.topbar.DeviceAppsTopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceAppsScreenScaffold(
    modifier: Modifier = Modifier,
    content: @Composable ((PaddingValues, Modifier) -> Unit) =
        { paddingValues, modifierInner -> DeviceAppsGeneralContent(paddingValues, modifierInner) }
) {
    // ViewModel берет приложения и загрузки из репозитория уровня приложения,
    // поэтому они сохраняются при уходе с экрана
    val application = LocalContext.current.applicationContext as GremApplication
    val viewState = viewModel { DeviceAppsViewState(application.appsRepository) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewState.appFilters, viewState.appDeviceFilter) {
        viewState.queryShownAppsByFilter()
    }

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


            if (viewState.showBottomSheet) {
                AppDeviceFilterBottomSheet(
                    sheetState = sheetState,
                    appDeviceFilter = viewState.appDeviceFilter,
                    onClickDeviceFilter = {
                        if (viewState.appDeviceFilter != AppDeviceFilter.ON_DEVICE) {
                            hideBottomSheet(scope, sheetState, viewState)
                            viewState.toggleAppDeviceFilter()

                        }
                    },
                    onClickNotInstalledFilter = {
                        if (viewState.appDeviceFilter != AppDeviceFilter.NOT_INSTALLED) {
                            hideBottomSheet(scope, sheetState, viewState)
                            viewState.toggleAppDeviceFilter()
                        }
                    },
                    onDismissRequest = { viewState.showBottomSheet = false },
                    menuModifier = modifier
                )

                LaunchedEffect(Unit) {
                    sheetState.expand()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun hideBottomSheet(scope: CoroutineScope, sheetState: SheetState, viewState: DeviceAppsViewState) {
    scope.launch {
        sheetState.hide()
    }.invokeOnCompletion {
        if (!sheetState.isVisible) {
            viewState.showBottomSheet = false
        }
    }
}