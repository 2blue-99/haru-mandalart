package com.coldblue.haru_mandalart.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import com.coldblue.haru_mandalart.navigation.HMDestination

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
        HMNaviHost()
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

        }
    }
}