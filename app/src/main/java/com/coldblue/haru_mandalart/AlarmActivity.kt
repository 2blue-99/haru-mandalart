package com.coldblue.haru_mandalart

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.blue.alarm.AlarmScreen
import com.coldblue.designsystem.theme.HarumandalartTheme

class AlarmActivity : ComponentActivity() {

    private var vibrator: Vibrator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val text = intent.getStringExtra("alarm_text") ?: ""

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        }
        vibrator = getSystemService(Vibrator::class.java) as Vibrator

        setContent {
            HarumandalartTheme {
                AlarmScreen(
                    title = text,
                    onFinished = ::finishActivity,
                    onStartActivity = ::startActivity
                )
            }
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
