package com.implus.gremmarket.ui.deviceapps.applist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.implus.gremmarket.R

@Composable
fun AppSampleRow() {
    val color = colorResource(R.color.DeviceAppsBottomBarBlue)
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .padding(start = 4.dp)
            .heightIn(min=60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            Modifier
                .size(42.dp)
                .background(color = color, shape = RoundedCornerShape(9.dp)),
            contentAlignment = Alignment.Center
        ) { }

        Spacer(Modifier.width(16.dp))
        Column(
            Modifier
                .weight(1f)
                .padding(vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.77f)
                    .height(15.dp)
                    .background(color = color, shape = RoundedCornerShape(2.dp))
            ) {}
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(12.dp)
                    .background(color = color, shape = RoundedCornerShape(2.dp))
            ) {}
        }
    }
}