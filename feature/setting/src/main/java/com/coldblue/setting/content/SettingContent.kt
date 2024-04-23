package com.coldblue.setting.content

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.data.util.LoginState
import com.coldblue.designsystem.component.HMTextDialog
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.setting.R

@Composable
fun SettingContent(
    navigateToNotice: () -> Unit,
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
            confirmText = stringResource(id = com.coldblue.designsystem.R.string.all_resign),
            tintColor = HMColor.Dark.Red,
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                deleteUser()
            },
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(HMColor.Light.Indigo)
    ) {
        item {
            Spacer(modifier = Modifier.size(20.dp))
        }
        item {
            SettingTile {
                Text(
                    text = "일반",
                    style = HmStyle.text20,
                    color = HMColor.Primary,
                    fontWeight = FontWeight.Bold
                )
                SettingItem(title = stringResource(id = R.string.account)) {
                    Text(text = email)
                }
                SettingItem(
                    title = "공지사항",
                    isClickable = true,
                    isLast = true,
                    onClick = { navigateToNotice() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "공지사항"
                    )
                }
            }
        }

        item {
            SettingTile {

                Text(
                    text = "피드백",
                    style = HmStyle.text20,
                    color = HMColor.Primary,
                    fontWeight = FontWeight.Bold
                )
                SettingItem(
                    title = "기능 제안하기",
                    isClickable = true,
                    onClick = { showPlayStore() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "기능 제안하기"
                    )
                }
                SettingItem(
                    title = stringResource(id = R.string.ask),
                    isClickable = true,
                    onClick = { showContact() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "문의"
                    )
                }
                SettingItem(
                    title = stringResource(id = R.string.evaluate),
                    isClickable = true,
                    isLast = true,
                    onClick = { showPlayStore() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "앱 평가"
                    )
                }
            }
        }

        item {
            SettingTile {

                Text(
                    text = "앱 정보",
                    style = HmStyle.text20,
                    color = HMColor.Primary,
                    fontWeight = FontWeight.Bold
                )

                SettingItem(
                    title = stringResource(id = R.string.version),
                ) {
                    Text(text = "v $versionName")
                }
                SettingItem(
                    title = stringResource(id = R.string.open_source),
                    isClickable = true,
                    isLast = true,
                    onClick = { showOss() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "오픈소스 라이센스"
                    )
                }
            }
        }

        item {
            SettingTile {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "계정관리",
                    style = HmStyle.text20,
                    color = HMColor.Primary,
                    fontWeight = FontWeight.Bold
                )
                if (loginState == LoginState.AuthenticatedLogin) {
                    SettingItem(
                        title = stringResource(id = R.string.resign),
                        isLast = false,
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
                            contentDescription = stringResource(id = R.string.resign)
                        )
                    }
                }
                if (loginState == LoginState.AuthenticatedLogin) {
                    SettingItem(
                        title = stringResource(id = R.string.logout),
                        color = HMColor.Dark.Red,
                        isLast = true,
                        isClickable = true,
                        onClick = { logout() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = stringResource(id = R.string.logout)
                        )
                    }
                } else {
                    SettingItem(
                        title = stringResource(id = R.string.login),
                        isLast = true,
                        isClickable = true,
                        onClick = { login() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = stringResource(id = R.string.login)
                        )
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.size(40.dp))
        }
    }
}

@Composable
fun SettingTile(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .background(HMColor.Background)
            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
    ) {
        content()
    }
    Spacer(modifier = Modifier.size(20.dp))
}

@Composable
fun SettingItem(
    title: String,
    color: Color = HMColor.Text,
    isLast: Boolean = false,
    isClickable: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(60.dp).padding(1.dp)
            .clickable(isClickable) {
                if (onClick != null) {
                    onClick()
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = title,
            style = HmStyle.text16,
            color = color
        )
        content()
    }
//    if (!isLast)
//        HorizontalDivider(
//            color = HMColor.SubLightText,
//        )
}

@Preview
@Composable
fun SettingContentPreview() {
    SettingContent(
        {},
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
        LoginState.AuthenticatedLogin,
    )
}


