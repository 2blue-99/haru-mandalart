package com.coldblue.data.work.init

import android.content.Context
import android.os.Build
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.coldblue.data.work.workers.SyncWorker

object Sync {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .apply { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) setRequiresDeviceIdle(false) }
        .build()

    fun initSyncRequest(context: Context){
        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "Sync",
            ExistingWorkPolicy.KEEP,
            syncRequest
        )
    }
}