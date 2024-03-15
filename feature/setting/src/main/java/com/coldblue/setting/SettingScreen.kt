package com.coldblue.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.setting.content.SettingContent

@Composable
fun SettingScreen(
    settingViewModel: SettingViewModel = hiltViewModel(),
) {
    val email by settingViewModel.email.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SettingContentWithState(
            showOss = settingViewModel::showOss,
            showPlayStore = settingViewModel::showPlayStore,
            showContact = settingViewModel::showContact,
            versionName = settingViewModel.versionName,
            logout = settingViewModel::logout,
            deleteUser = settingViewModel::deleteUser,
            email = email
        )
    }
}

@Composable
fun SettingContentWithState(
    showOss: () -> Unit,
    showPlayStore: () -> Unit,
    showContact: () -> Unit,
    versionName: String,
    logout: () -> Unit,
    deleteUser: () -> Unit,
    email: String
) {

    SettingContent(showOss, showPlayStore, showContact, versionName, logout, deleteUser, email)

}