package com.coldblue.haru_mandalart.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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

    override fun showPlayStore() {
        val packageName = context.packageName
        val playStoreUri = Uri.parse("market://details?id=$packageName")
        val playStoreIntent = Intent(Intent.ACTION_VIEW, playStoreUri)
        playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(playStoreIntent)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "플레이스토어 실행에 실패하였습니다.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}