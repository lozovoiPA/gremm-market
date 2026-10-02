package com.implus.gremmarket.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.R
import com.implus.gremmarket.ui.components.CategoryHeader
import com.implus.gremmarket.ui.components.HomeBottomBar
import com.implus.gremmarket.ui.components.HomeTabCarousel
import com.implus.gremmarket.ui.components.HomeTopBar
import com.implus.gremmarket.ui.components.gamecards.BigGameCardCarousel
import com.implus.gremmarket.ui.components.gamecards.ListCardsSection
import com.implus.gremmarket.ui.components.gamecards.MediumCardCarousel
import com.implus.gremmarket.ui.components.gamecards.SmallCardsCarousel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    topBar: @Composable (() -> Unit) = {}
) {
    val modifier = Modifier // Сдвиг элементов относительно системных треев
        .windowInsetsPadding(
            WindowInsets.safeDrawing.only(
                WindowInsetsSides.Horizontal
            )
        )
    Scaffold(
        containerColor = colorResource(R.color.MarketSystemArea)
    ) {
        contentPadding ->
        contentPadding
        val modifierInner = modifier
            .padding(
                start = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(LocalLayoutDirection.current)
            )
        Scaffold(
            modifier = modifier,
            containerColor = colorResource(R.color.MarketBackground),
            topBar = {
                Column() {
                    val topBarColors = TopAppBarDefaults.topAppBarColors().copy(
                        containerColor = colorResource(R.color.MarketBackground)
                    )
                    HomeTopBar(
                        modifierInner
                            .padding(
                                end = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(LocalLayoutDirection.current)
                            ),
                        topBarColors
                    )
                    HomeTabCarousel(
                        modifierInner
                    )
                    HorizontalDivider(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(0.dp)
                        ,
                        thickness = 1.dp,
                        color = Color.LightGray
                    )
                }
            },
            bottomBar = {
                HomeBottomBar(
                    modifierInner
                )
            }
        ) { contentPadding ->
            LazyColumn(
                modifier = modifierInner
                    .fillMaxSize()
                    .padding(contentPadding)
                    .consumeWindowInsets(contentPadding) // prevent children from accounting for that padding as well
                    .windowInsetsPadding( // add padding for content based on system UI for horizontal sides (padding at top already included vertical one)
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal
                        )
                    )
                    .background(colorResource(R.color.MarketBackground)),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    CategoryHeader("Специально для вас")
                    BigGameCardCarousel()
                }
                item {
                    CategoryHeader(title = "Специально для вас")
                    MediumCardCarousel()
                }

                // Секция 3: Рекомендуем (Маленькие карточки - 3 в ряд)
                item {
                    CategoryHeader(title = "Рекомендуем")
                    SmallCardsCarousel()
                }

                // Секция 4: Многопользовательские игры (Маленькие карточки с описанием)
                item {
                    CategoryHeader(title = "Многопользовательские игры")
                    ListCardsSection()
                }
            }

        }
    }
}