package com.implus.gremmarket.ui.components

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.R
import com.implus.gremmarket.TextSecondary

@Composable
fun HomeTabCarousel( modifier: Modifier) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Рекомендуем", "Лучшее", "ПК", "Другие устройства", "Детям", "Категории")
    PrimaryScrollableTabRow(
        selectedTab,
        modifier = modifier,
        containerColor = Color.White,
        contentColor = colorResource(R.color.MarketBlue),
        edgePadding = 0.dp,
        tabs = {
            tabs.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier
                        ,
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
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