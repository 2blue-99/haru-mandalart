package com.coldblue.setting.content

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.coldblue.designsystem.component.DeleteDialog
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.component.HMSwitch
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SettingContent(
    showOss: () -> Unit,
    showPlayStore: () -> Unit,
    showContact: () -> Unit,
    versionName: String,
    logout: () -> Unit,
    deleteUser: () -> Unit,
    onChangeAlarmState: (Boolean) -> Unit,
    email: String,
    alarm: Boolean
) {
    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        DeleteDialog(
            text = "탈퇴하면 모든 데이터가 완전히 삭제됩니다.",
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
            SettingItem(title = "앱 버전") {
                Text(text = "v $versionName")
            }
            SettingItem(
                title = "탈퇴",
                isLast = true,
                isClickable = true,
                onClick = { openDialog = true }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "탈퇴"
                )
            }
        }
        HMButton(text = "로그아웃", clickableState = true) {
            logout()
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

@Composable
fun PermissionCheck(context: Context, onChangeNotice: (Boolean) -> Unit) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            CoroutineScope(Dispatchers.IO).launch {
                onChangeNotice(isGranted)
            }
        }
    )

    LaunchedEffect(Unit) {
        Logger.d("들어옴1")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Logger.d("들어옴2")
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                Logger.d("들어옴3")
                onChangeNotice(true)
            }else {
                Logger.d("들어옴4")
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

@Preview
@Composable
fun SettingContentPreview() {
    SettingContent({}, {}, {}, "1.0", {}, {}, {},"hno05039@naver.com", false)

}


