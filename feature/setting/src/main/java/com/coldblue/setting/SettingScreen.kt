package com.coldblue.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.coldblue.setting.content.SettingContent

@Composable
fun SettingScreen(
    settingViewModel: SettingViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SettingContentWithState(
            showOss = settingViewModel::showOss,
            showPlayStore = settingViewModel::showPlayStore,
        )
    }
}

@Composable
fun SettingContentWithState(
    showOss: () -> Unit,
    showPlayStore: () -> Unit
) {

    SettingContent(showOss,showPlayStore)

}