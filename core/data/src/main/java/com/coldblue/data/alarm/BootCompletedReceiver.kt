package com.coldblue.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootCompletedReceiver :BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action==Intent.ACTION_BOOT_COMPLETED){
            Log.e("TAG", "onReceive: 부팅 완료", )
        }
    }

}