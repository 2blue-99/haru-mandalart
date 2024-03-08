package com.coldblue.haru_mandalart.util

import android.content.Context
import android.content.Intent
import com.coldblue.data.util.SettingHelper
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import javax.inject.Inject

class SettingHelperImpl @Inject constructor(
    val context: Context
) : SettingHelper {

    override fun showOss() {
        context.startActivity(
            Intent(context, OssLicensesMenuActivity::class.java).addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
            )
        )
    }
}