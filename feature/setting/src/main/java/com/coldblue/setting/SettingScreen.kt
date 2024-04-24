package com.coldblue.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.data.util.LoginState
import com.coldblue.setting.content.SettingContent
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle

@Composable
fun SettingScreen(
    navigateToNotice: () -> Unit,
    navigateToSurvey: () -> Unit,
    settingViewModel: SettingViewModel = hiltViewModel(),

) {
    val email by settingViewModel.email.collectAsStateWithLifecycle()
    val alarm by settingViewModel.alarm.collectAsStateWithLifecycle()

    val networkState by settingViewModel.isOnline.collectAsStateWithLifecycle()
    val loginState by settingViewModel.loginWithOutAuth.collectAsStateWithLifecycle()

    val authState = settingViewModel.getComposeAuth().rememberSignInWithGoogle(
        onResult = { result -> settingViewModel.checkLoginState(result) },
        fallback = { }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SettingContentWithState(
            navigateToNotice = navigateToNotice,
            navigateToSurvey = navigateToSurvey,
            showOss = settingViewModel::showOss,
            showPlayStore = settingViewModel::showPlayStore,
            showContact = settingViewModel::showContact,
            versionName = settingViewModel.versionName,
            logout = settingViewModel::logout,
            login = {authState.startFlow()},
            deleteUser = settingViewModel::deleteUser,
            onChangeAlarm = settingViewModel::updateAlarmState,
            email = email,
            alarm = alarm,
            networkState = networkState,
            loginState = loginState,
        )
    }
}

@Composable
fun SettingContentWithState(
    navigateToNotice: () -> Unit,
    navigateToSurvey: () -> Unit,

    showOss: () -> Unit,
    showPlayStore: () -> Unit,
    showContact: () -> Unit,
    versionName: String,
    logout: () -> Unit,
    login: () -> Unit,
    deleteUser: () -> Unit,
    onChangeAlarm: (Boolean) -> Unit,
    email: String,
    alarm: Boolean,
    networkState:Boolean,
    loginState: LoginState,
) {

    SettingContent(
        navigateToNotice,
        navigateToSurvey,
        showOss,
        showPlayStore,
        showContact,
        versionName,
        logout,
        login,
        deleteUser,
        onChangeAlarm,
        email,
        alarm,
        networkState,
        loginState
    )
}