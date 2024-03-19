package com.coldblue.haru_mandalart.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.coldblue.designsystem.component.HMNavigationBarItem
import com.coldblue.designsystem.component.HMTopBar
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.haru_mandalart.navigation.TopLevelDestination
import com.coldblue.haru_mandalart.navigation.HMNavHost

@Composable
fun HMApp(
    navController: HMAppState = rememberHMState()
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (navController.checkTopBar())
                HMTopBar {
                    navController.popBackStack()
                }
        },
        bottomBar = {
            if (navController.checkBottomNavBar())
                HMBottomBar(
                    destination = navController.bottomNavDestination,
                    navigate = navController::navigateToTopLevelDestination,
                    checkCurrentLocation = navController.currentLocation
                )

        }
    ) { padding ->
        HMNavHost(
            modifier = Modifier.padding(padding),
            appState = navController
        )
    }
}

@Composable
fun HMBottomBar(
    destination: List<TopLevelDestination>,
    navigate: (String) -> Unit,
    checkCurrentLocation: NavDestination?
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(color = HMColor.Box, thickness = 1.dp)
        NavigationBar(
            contentColor = HMColor.Primary,
            containerColor = HMColor.Background
        ) {
            destination.forEach { destination ->
                val isSelected = checkCurrentLocation?.route == destination.route
                HMNavigationBarItem(
                    selected = isSelected,
                    onClick = { navigate(destination.route) },
                    icon = if (isSelected) destination.selectedIcon else destination.unSelectedIcon,
                    label = destination.titleText
                )
            }
        }
    }
}