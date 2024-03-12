package com.coldblue.data.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.coldblue.data.sync.worker.SyncReadWorker

object SyncReadHelper {
    internal const val SYNC_READ = "SyncRead"
    internal val syncReadConstraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
    internal val readRequest = OneTimeWorkRequestBuilder<SyncReadWorker>().addTag(SYNC_READ)
        .setConstraints(syncReadConstraints)
        .build()


    fun initialize(context: Context) {
        WorkManager.getInstance(context).enqueueUniqueWork(
            SYNC_READ,
            ExistingWorkPolicy.KEEP,
            SyncReadWorker.startUpSyncWork()
        )
    }
}