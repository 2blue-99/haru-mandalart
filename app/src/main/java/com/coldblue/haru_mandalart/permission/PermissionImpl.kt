package com.coldblue.haru_mandalart.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import com.coldblue.data.util.PermissionHelper
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class PermissionImpl @Inject constructor(
    private val permissionHelper: PermissionHelper
) : Permission {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    override fun GetPermission(context: Context) {
//        val permissionLauncher = rememberLauncherForActivityResult(
//            contract = ActivityResultContracts.RequestPermission(),
//            onResult = { isGranted ->
//                CoroutineScope(Dispatchers.IO).launch {
//                    permissionHelper.updateInitPermissionState(true)
//                    permissionHelper.updateNoticePermissionState(isGranted)
//                }
//            }
//        )
//
//        DisposableEffect(Unit) {
//            Logger.d("들어옴2")
//            if(ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
//                Logger.d("들어옴3")
////                if (!permissionHelper.initPermissionState.first()) {
//                    Logger.d("들어옴4")
//                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
////                }
//            }
//            onDispose { }
//        }
//        Logger.d("들어옴5")


        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
//                hasNotificationPermission = isGranted
//                lifecycleScope.launch {
//                    permissionHelper.updateInitPermissionState(true)
//                    permissionHelper.updateNoticePermissionState(isGranted)
//                }
            }
        )

        LaunchedEffect(permissionLauncher) {
            Logger.d("들어옴1")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Logger.d("들어옴2")
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                )
                    Logger.d("들어옴3")
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
