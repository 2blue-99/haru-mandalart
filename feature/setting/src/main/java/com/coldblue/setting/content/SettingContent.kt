package com.coldblue.setting.content

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.data.util.LoginState
import com.coldblue.designsystem.component.HMTextDialog
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.component.HMSwitch
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.setting.R

@Composable
fun SettingContent(
    showOss: () -> Unit,
    showPlayStore: () -> Unit,
    showContact: () -> Unit,
    versionName: String,
    logout: () -> Unit,
    login: () -> Unit,
    deleteUser: () -> Unit,
    onChangeAlarmState: (Boolean) -> Unit,
    email: String,
    alarm: Boolean,
    networkState: Boolean,
    loginState: LoginState,
    ) {
    var openDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current


    if (openDialog) {

        HMTextDialog(
            targetText = "",
            text = stringResource(id = R.string.delete_dialog_title),
            confirmText = stringResource(id = R.string.resign),
            tintColors = HMColor.Dark.Red,
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                deleteUser()
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HMColor.Background)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            SettingItem(title = stringResource(id = R.string.notice)) {
                HMSwitch(checked = alarm) {
                    onChangeAlarmState(!alarm)
                }
            }
            SettingItem(title = stringResource(id = R.string.account)) {
                Text(text = email)
            }
            SettingItem(title = stringResource(id = R.string.ask), isClickable = true, onClick = { showContact() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "문의"
                )
            }
            SettingItem(title = stringResource(id = R.string.evaluate), isClickable = true, onClick = { showPlayStore() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "앱 평가"
                )
            }
            SettingItem(title = stringResource(id = R.string.open_source), isClickable = true, onClick = { showOss() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "오픈소스 라이센스"
                )
            }
            SettingItem(title = stringResource(id = R.string.version), isLast = loginState == LoginState.LoginWithOutAuth) {
                Text(text = "v $versionName")
            }
            if (loginState == LoginState.AuthenticatedLogin) {
                SettingItem(
                    title = stringResource(id = R.string.resign),
                    isLast = true,
                    isClickable = true,
                    onClick = {
                        if (networkState) {
                            openDialog = true
                        } else {
                            Toast.makeText(
                                context,
                                R.string.connection_err,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "탈퇴"
                    )
                }
            }

        }
        if (loginState == LoginState.AuthenticatedLogin) {
            HMButton(text = stringResource(id = R.string.logout), clickableState = true) {
                logout()
            }
        } else {
            HMButton(text = stringResource(id = R.string.login), clickableState = true) {
                login()
            }
        }

    }
}

@Composable
fun SettingItem(
    title: String,
    isLast: Boolean = false,
    isClickable: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(isClickable) {
                if (onClick != null) {
                    onClick()
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = HmStyle.text16)
        content()
    }
    if (!isLast)
        HorizontalDivider(
            color = HMColor.SubText,
        )
}

@Preview
@Composable
fun SettingContentPreview() {
    SettingContent(
        {},
        {},
        {},
        "1.0",
        {},
        {},
        {},
        {},
        "hno05039@naver.com",
        false,
        false,
        LoginState.LoginWithOutAuth,
    )
}


