package com.coldblue.haru_mandalart

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.coldblue.data.sync.SyncHelper
import com.coldblue.data.util.LoginHelper
import com.coldblue.data.util.LoginState
import com.coldblue.haru_mandalart.ui.HMApp
import com.coldblue.designsystem.theme.HarumandalartTheme
import com.coldblue.haru_mandalart.permission.PermissionImpl
import com.coldblue.login.LoginScreen
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var loginHelper: LoginHelper

    @Inject
    lateinit var syncHelper: SyncHelper

//    @Inject
//    lateinit var permissionHelper: PermissionHelper

    @Inject
    lateinit var permissionImpl: PermissionImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            HarumandalartTheme {

                val context = LocalContext.current
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Logger.d("들어옹ㅇㅇㅇ")
                    permissionImpl.GetPermission(context)
                }

//                var hasNotificationPermission by remember {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                        mutableStateOf(
//                            ContextCompat.checkSelfPermission(
//                                context,
//                                Manifest.permission.POST_NOTIFICATIONS
//                            ) == PackageManager.PERMISSION_GRANTED
//                        )
//                    } else {
//                        mutableStateOf(true)
//                    }
//                }
//                val permissionLauncher = rememberLauncherForActivityResult(
//                    contract = ActivityResultContracts.RequestPermission(),
//                    onResult = { isGranted ->
//                        hasNotificationPermission = isGranted
//                        lifecycleScope.launch {
//                            permissionHelper.updateInitPermissionState(true)
//                            permissionHelper.updateNoticePermissionState(isGranted)
//                        }
//                    }
//                )
//
//                LaunchedEffect(permissionLauncher) {
//                    Logger.d("들어옴1")
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                        Logger.d("들어옴2")
//                        if(ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
//                            Logger.d("들어옴3")
//                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//                    }
//                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BackOnPressed()
//                    HMApp()
                    loginHelper.isLogin.collectAsStateWithLifecycle(LoginState.Loading).value.let {
                        when (it) {
                            LoginState.Logout -> LoginScreen()
                            LoginState.Login -> {
                                syncHelper.initialize()
                                HMApp()
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun BackOnPressed() {
        val context = LocalContext.current
        var backPressedState by remember { mutableStateOf(true) }
        var backPressedTime = 0L

        BackHandler(enabled = backPressedState) {
            if (System.currentTimeMillis() - backPressedTime <= 1000L) {

                (context as Activity).finish()
            } else {
                backPressedState = true
                Toast.makeText(context, "한 번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
                backPressedTime = System.currentTimeMillis()
            }
        }
    }
}