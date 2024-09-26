package com.coldblue.haru_mandalart

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.res.stringResource
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.data.sync.SyncHelper
import com.coldblue.data.util.LoginHelper
import com.coldblue.data.util.LoginState
import com.coldblue.designsystem.theme.HarumandalartTheme
import com.coldblue.haru_mandalart.ui.HMApp
import com.coldblue.login.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var loginHelper: LoginHelper

    @Inject
    lateinit var syncHelper: SyncHelper

    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashScreen = installSplashScreen()
        splashScreen()

        setContent {
            CheckPermission()
            HarumandalartTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    BackOnPressed()
                    loginHelper.isLogin.collectAsStateWithLifecycle(LoginState.Loading).value.let {
                        when (it) {
                            LoginState.NoneAuthLogin -> HMApp()
                            LoginState.AuthenticatedLogin -> {
                                syncHelper.initialize().also { HMApp() }
                            }

                            LoginState.Logout -> LoginScreen()
                            LoginState.Loading -> {}
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onRestart() {
        super.onRestart()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_DENIED
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                loginHelper.updateAlarmState(true)
            }
        }
    }

    private fun splashScreen() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->

            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 0.96f, 0.93f, 0.96f, 1f)
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 0.96f, 0.93f, 0.96f, 1f)

            val bounce = ObjectAnimator.ofPropertyValuesHolder(
                splashScreenView.iconView,
                scaleX,
                scaleY
            )

            val fadeOut = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.ALPHA,
                0f
            )

            bounce.interpolator = AnticipateInterpolator()
            bounce.duration = 500L

            fadeOut.interpolator = AnticipateInterpolator()
            fadeOut.duration = 700L

            bounce.start()

            bounce.doOnEnd { fadeOut.start() }

            fadeOut.doOnEnd { splashScreenView.remove() }
        }
    }

    @Composable
    fun CheckPermission() {
        val context = LocalContext.current
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { result ->
                CoroutineScope(Dispatchers.IO).launch {
                    loginHelper.updateAlarmState(result)
                }
            }
        )
        LaunchedEffect(permissionLauncher) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    @Composable
    fun BackOnPressed() {
        val context = LocalContext.current
        var backPressedState by remember { mutableStateOf(true) }
        var backPressedTime = 0L
        val backNoticeMessage = stringResource(R.string.app_back_notice)
        BackHandler(enabled = backPressedState) {
            if (System.currentTimeMillis() - backPressedTime <= 1000L) {
                (context as Activity).finish()
            } else {
                backPressedState = true
                Toast.makeText(context, backNoticeMessage, Toast.LENGTH_SHORT).show()
                backPressedTime = System.currentTimeMillis()
            }
        }
    }
}


