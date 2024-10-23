package com.coldblue.haru_mandalart

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.blue.alarm.AlarmScreen
import com.coldblue.data.receiver.NOTICE_TITLE
import com.coldblue.designsystem.theme.HarumandalartTheme

class AlarmActivity : ComponentActivity() {

    private var vibrator: Vibrator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val text = intent.getStringExtra(NOTICE_TITLE) ?: ""

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        }
        vibrator = getSystemService(Vibrator::class.java) as Vibrator

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        setContent {
            HarumandalartTheme {

                BackOnPressed()

                AlarmScreen(
                    title = text,
                    onFinished = ::finishActivity,
                    onStartActivity = ::startActivity
                )
            }
        }
    }

    @Composable
    fun BackOnPressed() {
        val context = LocalContext.current
        var backPressedState by remember { mutableStateOf(true) }
        val backNoticeMessage = stringResource(R.string.alarm_back_notice)
        BackHandler(enabled = backPressedState) {
            Toast.makeText(context, backNoticeMessage, Toast.LENGTH_SHORT).show()
        }
    }
    override fun onResume() {
        super.onResume()

        val timings = longArrayOf(1200, 150, 300)
        val amplitudes = intArrayOf(0, 100, 255)

        val effect = VibrationEffect.createWaveform(timings, amplitudes, 0)

        vibrator?.vibrate(effect)
    }

    private fun finishActivity(){
        vibrator?.cancel()
        this.finish()
    }

    private fun startActivity(){
        vibrator?.cancel()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
