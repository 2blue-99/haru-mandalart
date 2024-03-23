package com.coldblue.haru_mandalart.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.coldblue.data.util.SettingHelper
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import javax.inject.Inject

class SettingHelperImpl @Inject constructor(
    val context: Context,
) : SettingHelper {

    override val versionName: String =
        context.packageManager.getPackageInfo(context.packageName, 0).versionName


    override fun showOss() {
        context.startActivity(
            Intent(context, OssLicensesMenuActivity::class.java).addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
            )
        )
    }

    override fun showContact() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf("hno05039@naver.com", "lpm083100@gmail.com")
            )
            type = "plain/Text"

            putExtra(Intent.EXTRA_SUBJECT, "[하루 만다라트] 문의")
            putExtra(Intent.EXTRA_TEXT, "문의내용: ")
            setPackage("com.google.android.gm")
        }

        try {
            context.startActivity(
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        } catch (e: Exception) {
            Toast.makeText(context, "메일을 전송할 수 없습니다.", Toast.LENGTH_LONG).show()
        }
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