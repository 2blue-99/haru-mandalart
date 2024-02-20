package com.coldblue.haru_mandalart.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import com.coldblue.designsystem.component.HMNavigationBarItem
import com.coldblue.haru_mandalart.navigation.HMDestination
import com.coldblue.haru_mandalart.navigation.HMNavHost

@Composable
fun HMApp(
    navController: HMAppState = rememberHMState()
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            HMBottomBar(
                destination = navController.destination,
                navigate = navController::navigationToDestination,
                checkCurrentLocation = navController.currentLocation
            )
        }
    ) {
        padding ->
        HMNavHost(
            modifier = Modifier.padding(padding),
            appState = navController
        )
    }
}

@Composable
fun HMBottomBar(
    destination: List<HMDestination>,
    navigate: (String) -> Unit,
    checkCurrentLocation: NavDestination?
){
    NavigationBar {
        destination.forEach { destination ->
            val isSelected = checkCurrentLocation?.route == destination.titleTextId
            HMNavigationBarItem(
                selected = isSelected,
                onClick = { navigate(destination.titleTextId) },
                icon = if(isSelected) destination.selectedIcon else destination.unSelectedIcon,
                label = destination.titleTextId
            )
        }
    }
}