package com.coldblue.data.util

import kotlinx.coroutines.flow.Flow


interface SettingHelper {

    val versionName: String
    fun showOss()
    fun showContact()
    fun showPlayStore()


}