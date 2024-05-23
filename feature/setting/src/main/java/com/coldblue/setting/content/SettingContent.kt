package com.coldblue.setting.content

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
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
import com.coldblue.designsystem.component.BottomSpacer
import com.coldblue.designsystem.component.HMSwitch
import com.coldblue.designsystem.component.HMTextDialog
import com.coldblue.designsystem.component.HMTopBar
import com.coldblue.designsystem.component.TopSpacer
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.setting.R
import com.coldblue.setting.state.DialogType

@Composable
fun SettingContent(
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
    onChangeAlarmState: (Boolean) -> Unit,
    email: String,
    alarm: Boolean,
    networkState: Boolean,
    loginState: LoginState,
    initManda: () -> Unit,
) {
    var openDialog by remember { mutableStateOf(Pair(false, DialogType.Logout)) }
    val context = LocalContext.current


    if (openDialog.first) {
        when (openDialog.second) {
            DialogType.Logout -> {
                LogoutDialog(
                    onDismiss = { openDialog = openDialog.copy(false) },
                    onResign = logout
                )
            }

            DialogType.Resign -> {
                ResignDialog(
                    onDismiss = { openDialog = openDialog.copy(false) },
                    deleteUser = deleteUser
                )
            }

            DialogType.Init -> {
                InitDialog(
                    onDismiss = { openDialog = openDialog.copy(false) },
                    initManda = {
                        openDialog = openDialog.copy(false)
                        initManda()
                        navigateToBackStack()
                    }
                )
            }
        }

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HMColor.Box)

    ) {
        HMTopBar(title = stringResource(id = R.string.setting_title)) {
            navigateToBackStack()
        }
        LazyColumn {
            item { TopSpacer() }
            item {
                SettingTile(stringResource(id = R.string.setting_mandalart)) {
                    SettingItem(title = stringResource(id = R.string.setting_init),
                        isClickable = true,
                        isLast = true,
                        onClick = { openDialog = Pair(true, DialogType.Init) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "초기화"
                        )
                    }

                }
            }
            item {
                SettingTile(stringResource(id = R.string.setting_general)) {
                    SettingItem(title = stringResource(id = R.string.setting_account)) {
                        Text(text = email)
                    }
                    SettingItem(title = "알림") {
                        HMSwitch(checked = alarm) {
                            onChangeAlarmState(!alarm)
                        }
                    }
                    SettingItem(
                        title = stringResource(id = R.string.setting_notice),
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
                SettingTile(stringResource(id = R.string.setting_feedback)) {
                    SettingItem(
                        title = stringResource(id = R.string.setting_survey),
                        isClickable = true,
                        onClick = { navigateToSurvey() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "기능 제안하기"
                        )
                    }
                    SettingItem(
                        title = stringResource(id = R.string.setting_ask),
                        isClickable = true,
                        onClick = { showContact() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "문의"
                        )
                    }
                    SettingItem(
                        title = stringResource(id = R.string.setting_evaluate),
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
                SettingTile(stringResource(id = R.string.setting_information)) {
                    SettingItem(
                        title = stringResource(id = R.string.setting_version),
                    ) {
                        Text(text = "v $versionName")
                    }
                    SettingItem(
                        title = stringResource(id = R.string.setting_open_source),
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
                SettingTile(stringResource(id = R.string.setting_manage_account)) {

                    if (loginState == LoginState.AuthenticatedLogin) {
                        SettingItem(
                            title = stringResource(id = R.string.setting_logout),
                            isLast = true,
                            isClickable = true,
                            onClick = { openDialog = Pair(true, DialogType.Logout) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = stringResource(id = R.string.setting_logout)
                            )
                        }
                    } else {
                        SettingItem(
                            title = stringResource(id = R.string.setting_login),
                            isLast = true,
                            isClickable = true,
                            onClick = {
                                if (networkState) {
                                    login()
                                } else {
                                    Toast.makeText(
                                        context,
                                        R.string.setting_connection_err,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = stringResource(id = R.string.setting_login)
                            )
                        }
                    }
                    if (loginState == LoginState.AuthenticatedLogin) {
                        SettingItem(
                            title = stringResource(id = com.coldblue.designsystem.R.string.all_resign),
                            color = HMColor.Manda.Red,
                            isLast = false,
                            isClickable = true,
                            onClick = {
                                if (networkState) {
                                    openDialog = Pair(true, DialogType.Resign)
                                } else {
                                    Toast.makeText(
                                        context,
                                        R.string.setting_connection_err,
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
            }
            item {
                BottomSpacer()
            }
        }

    }
}

@Composable
fun SettingTile(
    text: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .background(HMColor.Background)
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp),
            text = text,
            style = HmStyle.text12,
            color = HMColor.Primary,
            fontWeight = FontWeight.Bold
        )
        content()
    }
    TopSpacer()
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
            .height(60.dp)
            .clickable(isClickable) {
                if (onClick != null) {
                    onClick()
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(start = 32.dp),
            text = title,
            style = HmStyle.text16,
            color = color
        )
        Box(modifier = Modifier.padding(end = 16.dp)) {
            content()
        }
    }
//    if (!isLast)
//        HorizontalDivider(
//            color = HMColor.SubLightText,
//        )
}

@Composable
fun ResignDialog(
    onDismiss: () -> Unit,
    deleteUser: () -> Unit
) {
    HMTextDialog(
        targetText = "",
        text = stringResource(id = R.string.delete_dialog_resign),
        confirmText = stringResource(id = com.coldblue.designsystem.R.string.all_resign),
        tintColor = HMColor.Manda.Red,
        onDismissRequest = {
            onDismiss()
        },
        onConfirmation = {
            deleteUser()
        },
    )
}

@Composable
fun LogoutDialog(
    onDismiss: () -> Unit,
    onResign: () -> Unit
) {
    HMTextDialog(
        targetText = "",
        text = stringResource(id = R.string.delete_dialog_logout),
        confirmText = stringResource(id = R.string.setting_logout),
        tintColor = HMColor.Manda.Red,
        onDismissRequest = {
            onDismiss()
        },
        onConfirmation = {
            onResign()
        },
    )
}

@Composable
fun InitDialog(
    onDismiss: () -> Unit,
    initManda: () -> Unit
) {
    HMTextDialog(
        targetText = "",
        text = stringResource(id = R.string.delete_dialog_init),
        confirmText = stringResource(id = R.string.setting_init),
        tintColor = HMColor.Manda.Red,
        onDismissRequest = {
            onDismiss()
        },
        onConfirmation = {
            initManda()
        },
    )
}

@Preview
@Composable
fun SettingContentPreview() {
    SettingContent(
        {},
        {},
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
        {},
    )
}


