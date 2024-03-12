package com.coldblue.data.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.coldblue.data.sync.SyncReadHelper.SYNC_READ
import com.coldblue.data.sync.worker.SyncReadWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SyncWriteHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SyncWriteHelper {
    internal val SYNC_WRITE = "SyncWrite"
    internal val syncWriteConstraints
        get() = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

    internal val writeRequest = OneTimeWorkRequestBuilder<SyncReadWorker>().addTag(SYNC_WRITE)
        .setConstraints(syncWriteConstraints)
        .build()

    override fun syncWrite() {
        WorkManager.getInstance(context).beginUniqueWork(
            SYNC_READ + SYNC_WRITE,
            ExistingWorkPolicy.KEEP,
            SyncReadHelper.readRequest
        ).then(writeRequest).enqueue()
    }
}