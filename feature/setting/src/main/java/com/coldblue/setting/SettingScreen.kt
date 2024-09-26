package com.coldblue.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.component.HMTextDialog
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.setting.content.SettingContent
import com.coldblue.setting.state.SettingUIState
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle

@Composable
fun SettingScreen(
    navigateToNotice: () -> Unit,
    navigateToSurvey: () -> Unit,
    navigateToBackStack: () -> Unit,
    settingViewModel: SettingViewModel = hiltViewModel(),
) {

    val settingUIState by settingViewModel.settingUIState.collectAsStateWithLifecycle()
    val permissionDialogState by settingViewModel.permissionDialogState.collectAsStateWithLifecycle()

    val authState = settingViewModel.getComposeAuth().rememberSignInWithGoogle(
        onResult = { result -> settingViewModel.checkLoginState(result) },
        fallback = { }
    )
    if (permissionDialogState) {
        HMTextDialog(
            targetText = "알림권한",
            bottomText = "을 허용해 주세요",
            confirmText = "설정이동",
            tintColor = HMColor.Primary,
            onDismissRequest = {
                settingViewModel.hidePermissionDialog()
            },
            onConfirm = {
                settingViewModel.hidePermissionDialog()
                settingViewModel.showAppInfo()
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SettingContentWithState(
            settingUIState = settingUIState,
            navigateToNotice = navigateToNotice,
            navigateToSurvey = navigateToSurvey,
            navigateToBackStack = navigateToBackStack,
            showOss = settingViewModel::showOss,
            showPlayStore = settingViewModel::showPlayStore,
            showContact = settingViewModel::showContact,
            versionName = settingViewModel.versionName,
            logout = settingViewModel::logout,
            login = { authState.startFlow() },
            deleteUser = settingViewModel::deleteUser,
            onChangeAlarm = settingViewModel::updateAlarmState,
            initManda = settingViewModel::initManda,
        )
    }
}

@Composable
fun SettingContentWithState(
    settingUIState: SettingUIState,
    navigateToNotice: () -> Unit,
    navigateToSurvey: () -> Unit,
    navigateToBackStack: () -> Unit,
    showOss: () -> Unit,
    showPlayStore: () -> Unit,
    showContact: () -> Unit,
    versionName: String,
    logout: () -> Unit,
    login: () -> Unit,
    deleteUser: () -> Unit,
    onChangeAlarm: (Boolean) -> Unit,
    initManda: () -> Unit,
) {
    when (settingUIState) {
        is SettingUIState.Loading -> {}
        is SettingUIState.Error -> {}
        is SettingUIState.Success -> {
            SettingContent(
                navigateToNotice,
                navigateToSurvey,
                navigateToBackStack,
                showOss,
                showPlayStore,
                showContact,
                versionName,
                logout,
                login,
                deleteUser,
                onChangeAlarm,
                settingUIState.email,
                settingUIState.isAlarm,
                settingUIState.isOnline,
                settingUIState.loginWithOutAuth,
                initManda
            )
        }
    }


}