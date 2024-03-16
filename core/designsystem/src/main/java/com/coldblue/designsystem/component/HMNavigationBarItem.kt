package com.coldblue.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.theme.HMColor

@Composable
fun RowScope.HMNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String
) {
    NavigationBarItem(
        selected = selected,
        onClick = { onClick() },
        icon = {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = icon,
                contentDescription = null
            )
        },
        modifier = Modifier,
        label = { Text(text = label, fontWeight = FontWeight.Bold) },
        alwaysShowLabel = true,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = HMColor.Primary,
            unselectedIconColor = HMColor.Gray,
            selectedTextColor = HMColor.Primary,
            unselectedTextColor = HMColor.Gray,
        )
    )
}