package com.implus.gremmarket.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.implus.gremmarket.ui.deviceapps.AppInfo

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
                    CategoryHeader("Премиум-игры")
                    //Spacer(modifier = Modifier.height(2.dp))
                    BigGameCardCarousel()
                    Spacer(modifier = Modifier.height(18.dp))
                }
                item {
                    CategoryHeader(title = "Специально для вас", false)
                    Spacer(modifier = Modifier.height(8.dp))
                    MediumCardCarousel()
                    Spacer(modifier = Modifier.height(15.dp))
                }

                item {
                    CategoryHeader(title = "Рекомендуем")
                    ListCardsSection(recommendedApps)
                }
            }

        }
    }
}

val recommendedApps = listOf(
    AppInfo(
        name = "Candy Crush Soda Saga",
        shortDescription = "Начните играть в эту вкусную игру типа \"3 в ряд\" уже сегодня! Содалиссимо!",
        rating = "4,0",
        icon = "\uD83E\uDD64",
        size = "",
        iconBgColor = Color(0xFFC556D6)
    ),
    AppInfo(
        name = "Jewel Legend: три в ряд игры",
        shortDescription = "Классические игры три в ряд без интернета! Развивает логику и успокаивает нервы!",
        rating = "4,8",
        icon = "\uD83D\uDC8E",
        size = "",
        iconBgColor = Color(0xFF0F52BA)
    ),
    AppInfo(
        name = "Измерения Антиматерии",
        shortDescription = "Как много Антиматерии вы сможете создать?",
        rating = "4,7",
        icon = "\u269B\uFE0F",
        size = "56 МБ",
        iconBgColor = Color(0xFFFFFFFF)
    ),
    AppInfo(
        name = "ПИФЛИ",
        shortDescription = "Готовься, целься, пли! Сыграйте в Piffle, аркадную игру-головоломку с котиками.",
        rating = "4,4",
        icon = "\uD83D\uDC31",
        size = "129 МБ",
        iconBgColor = Color(0xFFFFF59D)
    ),
    AppInfo(
        name = "Нонограм - Японский кроссворд",
        shortDescription = "Решайте нонограммы - головоломки с цифрами! Открывайте новые изображения!",
        rating = "4,3",
        icon = "\uD83E\uDDE9",
        size = "",
        iconBgColor = Color(0xFFE0E0E0)
    ),
    AppInfo(
        name = "Chillquarium",
        shortDescription = "Уютный idle: разводи рыб, расширяй аквариумы, собирай редких",
        rating = "4,5",
        icon = "\uD83D\uDC1F",
        size = "",
        iconBgColor = Color(0xFF00897B)
    ),
    AppInfo(
        name = "Postknight 2",
        shortDescription = "Пройди путь героя от начала до конца в казуальном RPG-приключении.",
        rating = "4,9",
        icon = "\uD83D\uDC70",
        size = "558 МБ",
        iconBgColor = Color(0xFFFFCDD2)
    ),
    AppInfo(
        name = "Art of Solitaire - Пасьянсы",
        shortDescription = "Карточные игры • Пасьянс \"Паук\"",
        rating = "4,1",
        icon = "\uD83C\uDCA1",
        size = "85 МБ",
        iconBgColor = Color(0xFF8D6E63)
    ),
    AppInfo(
        name = "Skullgirls: РПГ-файтинг",
        shortDescription = "ПОТРЯСАЮЩИЙ мобильный файтинг! Собирай, улучшай, персонализируй и ДЕР",
        rating = "3,9",
        icon = "\uD83D\uDC80",
        size = "",
        iconBgColor = Color(0xFFD32F2F)
    ),
    AppInfo(
        name = "AFK Arena",
        shortDescription = "Начните свое эпическое RPG-приключение с 1000 призов.",
        rating = "4,7",
        icon = "\uD83D\uDEE1\uFE0F",
        size = "267 МБ",
        iconBgColor = Color(0xFFFFD54F)
    ),
    AppInfo(
        name = "TripleFantasy Premium",
        shortDescription = "Начните с 999 призов! Соберите свою колоду пиксельных героев и разработайте стратегию.",
        rating = "3,3",
        icon = "\uD83D\uDC51",
        size = "302 МБ",
        iconBgColor = Color(0xFFF48FB1)
    ),
    AppInfo(
        name = "Sword X Staff",
        shortDescription = "РПГ третьего пути",
        rating = "4,8",
        icon = "\uD83D\uDDE1\uFE0F",
        size = "3,1 ГБ",
        iconBgColor = Color(0xFF90CAF9)
    )
)