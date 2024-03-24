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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.data.util.LoginState
import com.coldblue.designsystem.component.DeleteDialog
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.component.HMSwitch
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle

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

        DeleteDialog(
            targetText = "",
            text = "탈퇴하면 모든 데이터가 완전히 삭제돼요.",
            deleteConfirmText = "탈퇴",
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
            SettingItem(title = "알림") {
                HMSwitch(checked = alarm) {
                    onChangeAlarmState(!alarm)
                }
            }
            SettingItem(title = "현재계정") {
                Text(text = email)
            }
            SettingItem(title = "문의하기", isClickable = true, onClick = { showContact() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "문의"
                )
            }
            SettingItem(title = "앱 평가하기", isClickable = true, onClick = { showPlayStore() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "앱 평가"
                )
            }
            SettingItem(title = "오픈소스 라이센스", isClickable = true, onClick = { showOss() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "오픈소스 라이센스"
                )
            }
            SettingItem(title = "앱 버전", isLast = loginState == LoginState.LoginWithOutAuth) {
                Text(text = "v $versionName")
            }
            if (loginState == LoginState.AuthenticatedLogin) {
                SettingItem(
                    title = "탈퇴",
                    isLast = true,
                    isClickable = true,
                    onClick = {
                        if (networkState) {
                            openDialog = true
                        } else {
                            Toast.makeText(
                                context,
                                " 인터넷 연결을 확인하세요",
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
            HMButton(text = "로그아웃", clickableState = true) {
                logout()
            }
        } else {
            HMButton(text = "로그인", clickableState = true) {
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


