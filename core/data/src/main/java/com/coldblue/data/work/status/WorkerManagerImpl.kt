package com.coldblue.data.work.status

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.coldblue.data.work.init.Sync
import com.coldblue.data.work.workers.SyncWorker
import com.coldblue.data.work.workers.WriteWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WorkerManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
): WorkerManager {
    override fun syncRequest() {
        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(Sync.constraints)
            .build()

        val writeRequest = OneTimeWorkRequestBuilder<WriteWorker>()
            .setConstraints(Sync.constraints)
            .build()

        WorkManager.getInstance(context).beginUniqueWork(
            "Sync",
            ExistingWorkPolicy.KEEP,
            syncRequest
        ).then(writeRequest).enqueue()
    }
}