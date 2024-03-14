package com.coldblue.data.sync.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.coldblue.data.repository.todo.CurrentGroupRepository
import com.coldblue.data.repository.todo.CurrentGroupRepositoryImpl
import com.coldblue.data.repository.todo.TodoGroupRepository
import com.coldblue.data.repository.todo.TodoRepository
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
    @Assisted private val todoGroupRepository: TodoGroupRepository,
    @Assisted private val currentGroupRepository: CurrentGroupRepository,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val syncedSucceed = awaitAll(
                async { todoRepository.syncRead() },
                async { todoGroupRepository.syncRead() },
                async { currentGroupRepository.syncRead() },

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
}