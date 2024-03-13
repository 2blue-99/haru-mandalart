package com.coldblue.data.sync.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.coldblue.data.repository.todo.TodoRepository
import com.coldblue.data.sync.SyncReadHelper.SYNC_READ
import com.coldblue.data.sync.SyncReadHelper.syncReadConstraints
import com.orhanobut.logger.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext


@HiltWorker
class SyncReadWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Assisted private val todoRepository: TodoRepository,

    ) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val syncedSucceed = awaitAll(
                async { todoRepository.syncRead() },
            ).all { it }
            if (syncedSucceed) {
                Logger.d("읽기 성공")
                
                Result.success()

            } else {
                Result.retry()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<SyncReadWorker>().addTag(SYNC_READ)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(syncReadConstraints)
            .build()
    }
}