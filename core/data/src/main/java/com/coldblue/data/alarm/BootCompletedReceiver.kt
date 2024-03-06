package com.coldblue.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootCompletedReceiver :BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action==Intent.ACTION_BOOT_COMPLETED){
            //TODO 기기 종료시 알람 다 삭제됨 알람 다시 추가해야함
            Log.e("TAG", "onReceive: 부팅 완료", )
        }
    }

}