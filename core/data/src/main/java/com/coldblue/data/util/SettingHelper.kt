package com.coldblue.data.util


interface SettingHelper {

    val versionName: String
    fun startApp()
    fun showOss()
    fun showContact()
    fun showPlayStore()
    fun checkAlarmPermission():Boolean
    fun showAppInfo()
}