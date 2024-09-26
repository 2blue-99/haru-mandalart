package com.coldblue.haru_mandalart.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class AppLifecycleObserver: LifecycleEventObserver {
    var isAppRunning = false
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        isAppRunning = when(event){
            Lifecycle.Event.ON_RESUME -> {
                com.orhanobut.logger.Logger.d("ON_RESUME")
                true
            }
            else -> {
                com.orhanobut.logger.Logger.d("$event")
                false
            }
        }
    }

}