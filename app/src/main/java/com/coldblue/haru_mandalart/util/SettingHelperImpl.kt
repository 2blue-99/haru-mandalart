package com.coldblue.haru_mandalart.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.res.stringResource
import com.coldblue.data.util.SettingHelper
import com.coldblue.haru_mandalart.R
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
                arrayOf(R.string.chan_mail, R.string.pu_mail)
            )
            type = "plain/Text"

            putExtra(Intent.EXTRA_SUBJECT, R.string.mail_title)
            putExtra(Intent.EXTRA_TEXT, R.string.mail_content)
            setPackage("com.google.android.gm")
        }

        try {
            context.startActivity(
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        } catch (e: Exception) {
            Toast.makeText(context, R.string.fail_mail, Toast.LENGTH_LONG).show()
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
                R.string.fail_playStore,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}