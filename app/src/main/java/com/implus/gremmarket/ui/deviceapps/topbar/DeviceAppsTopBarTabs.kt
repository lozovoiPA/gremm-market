package com.implus.gremmarket.ui.deviceapps.topbar

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.implus.gremmarket.DeviceAppsLocalNavigation
import com.implus.gremmarket.R
import com.implus.gremmarket.TextSecondary
import com.implus.gremmarket.ui.Destination

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