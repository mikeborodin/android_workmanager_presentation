package com.example.workmanagertutorial.data.db


import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.workmanagertutorial.entity.*
import kotlinx.coroutines.runBlocking


class PostWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    lateinit var repository: Repository

    override fun doWork(): Result {
        val path = inputData.getString(REMOTE_FILE_URI) ?: return Result.failure()
        if (path.isEmpty()) return Result.failure()

        val labels = inputData.getStringArray(IMAGE_LABELS)?.toList() ?: listOf()

        val media = Media(path, labels)

        return try {
            runBlocking {
                repository.create(media)
                Result.success(workDataOf(REMOTE_FILE_URI to path, IMAGE_LABELS to labels.toTypedArray()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}