package com.coldblue.data.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.coldblue.data.sync.worker.SyncReadWorker
import com.orhanobut.logger.Logger

object SyncReadHelper {
    internal const val SYNC_READ = "SyncRead"

    internal val syncReadConstraints get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    fun readRequest(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<SyncReadWorker>().addTag(SYNC_READ)
            .setConstraints(syncReadConstraints)
            .build()
    }


    fun initialize(context: Context) {
        WorkManager.getInstance(context).enqueueUniqueWork(
            SYNC_READ,
            ExistingWorkPolicy.KEEP,
            SyncReadWorker.startUpSyncWork()
        )
    }
}