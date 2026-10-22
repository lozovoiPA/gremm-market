package com.implus.gremmarket.ui.deviceapps.applist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.R
import com.implus.gremmarket.ui.deviceapps.AppDeviceFilter
import com.implus.gremmarket.ui.deviceapps.AppFilters
import com.implus.gremmarket.ui.deviceapps.DeviceAppsViewState
import com.implus.gremmarket.ui.deviceapps.isFilterToggled
import com.implus.gremmarket.ui.deviceapps.toggleFilter

@Composable
fun AppFiltersChipsRow(
    viewState: DeviceAppsViewState,
    onAppDeviceFilterChipClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val appDeviceFilter = viewState.appDeviceFilter

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
                val selected = viewState.appFilters.isFilterToggled(filter) // проверка включен ли фильтр (номер в enum определяет бит флага)
                val chipModifier =
                    if (index == AppFilters.entries.lastIndex)
                        Modifier.padding(end = 10.dp)
                    else
                        Modifier

                FilterChip(
                    modifier = chipModifier,
                    selected = selected,
                    onClick = {
                        viewState.appFilters = viewState.appFilters.toggleFilter(filter)
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