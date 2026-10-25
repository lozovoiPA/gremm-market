package com.implus.gremmarket.ui.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.implus.gremmarket.R
import com.implus.gremmarket.ui.components.MenuGroup
import com.implus.gremmarket.ui.components.MenuItemData
import com.implus.gremmarket.ui.components.RowMenuItem

@Composable
fun AccountHeader(currentAccount: Account, otherAccount: Account) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Avatar(account = currentAccount, size = 80.dp)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Здравствуйте, ${currentAccount.name}!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {},
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .heightIn(min=45.dp),
            border = BorderStroke(1.dp, Color.Gray),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF0F6B3B))
        ) {
            Text("Управление аккаунтом Grem")
        }

        Spacer(modifier = Modifier.height(16.dp))

        MenuGroup(
            listOf(
                MenuItemData(
                    title = "Сменить аккаунт",
                    trailingContent = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Avatar(account = otherAccount, size = 24.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(25.dp)
                                    .clip(CircleShape)
                                    .background(colorResource(R.color.AccountBackground))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )
            )
        )

        Text(
            text = "Другие действия в приложении \"Grem Market\"",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 38.dp, vertical = 16.dp),
            color = Color.DarkGray,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = (MaterialTheme.typography.bodyMedium.fontSize.value + 1).sp
        )

        val menuItem = MenuItemData(
            iconComposable = {
                Icon(
                    imageVector = Icons.Default.VerifiedUser,
                    contentDescription = null,
                    tint = Color(0xFF4285F4),
                    modifier = Modifier
                        .size(32.dp)
                        .offset(16.dp)
                )
            },
            title = "Платите быстро и безопасно",
            subtitle = "Настройте проверку сейчас для быстрого оформления покупок в будущем.",
            trailingContent = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp)
                ) {
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F6B3B)),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text("Продолжить")
                    }
                }

            }
        )

        MenuGroup(
            {
                RowMenuItem(
                    menuItem,
                    clickIndication = ripple(color = colorResource(R.color.ClickRippleGreen)),
                    textOffset = -8,
                    titleStyle = MaterialTheme.typography.labelMedium,
                    subtitleStyle = MaterialTheme.typography.labelSmall)
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(30.dp))
        )

        Spacer(modifier = Modifier.height(14.dp))

        MenuGroup(
            listOf(
                MenuItemData(
                    iconComposable = {
                        Icon(
                            imageVector = Icons.Default.SportsEsports,
                            contentDescription = null,
                            tint = Color(0xFF0F9D58),
                            modifier = Modifier.size(32.dp)
                        )
                    },
                    title = "VividEgoist",
                    subtitle = "Профиль Grem Play Games",
                    trailingContent = {
                        Avatar(account = Account("V", "", Color(0xFF673AB7)), size = 32.dp)
                    }
                )
            ),
            modifier = Modifier.heightIn(max = 60.dp)
        )
    }
}