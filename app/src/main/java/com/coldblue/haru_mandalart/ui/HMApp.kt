package com.coldblue.haru_mandalart.ui

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import com.coldblue.designsystem.component.HMNavigationBarItem
import com.coldblue.haru_mandalart.navigation.HMDestination
import com.coldblue.haru_mandalart.navigation.HMNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HMApp(
    navController: HMAppState = rememberHMState()
) {
    BackOnPressed()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (navController.checkTopBar()) {
                TopAppBar(
                    title = { Text(text = "설정") },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray),
                    navigationIcon = {
                        Button(onClick = { navController.navController.popBackStack() }) { Text(text = "Back") }
                    },
                )
            }
        },
        bottomBar = {
            if (navController.checkBottomNavBar()) {
                HMBottomBar(
                    destination = navController.bottomNavDestination,
                    navigate = navController::navigationToDestination,
                    checkCurrentLocation = navController.currentLocation
                )
            }
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
    destination: List<HMDestination>,
    navigate: (String) -> Unit,
    checkCurrentLocation: NavDestination?
) {
    NavigationBar {
        destination.forEach { destination ->
            val isSelected = checkCurrentLocation?.route == destination.titleTextId
            HMNavigationBarItem(
                selected = isSelected,
                onClick = { navigate(destination.titleTextId) },
                icon = if (isSelected) destination.selectedIcon else destination.unSelectedIcon,
                label = destination.titleTextId
            )
        }
    }
}

@Composable
fun BackOnPressed() {
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L

    BackHandler(enabled = backPressedState) {
        if (System.currentTimeMillis() - backPressedTime <= 400L) {

            (context as Activity).finish()
        } else {
            backPressedState = true
            Toast.makeText(context, "한 번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
            backPressedTime = System.currentTimeMillis()
        }
    }

}