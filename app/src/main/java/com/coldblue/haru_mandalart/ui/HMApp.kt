package com.coldblue.haru_mandalart.ui

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import com.coldblue.designsystem.component.HMNavigationBarItem
import com.coldblue.designsystem.component.HMTopBar
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
                HMTopBar { navController.popBackStack() }
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
    NavigationBar {
        destination.forEach { destination ->
            val isSelected = checkCurrentLocation?.route == destination.titleText
            HMNavigationBarItem(
                selected = isSelected,
                onClick = { navigate(destination.titleText) },
                icon = if (isSelected) destination.selectedIcon else destination.unSelectedIcon,
                label = destination.titleText
            )
        }
    }
}