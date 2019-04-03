package com.example.workmanagertutorial.data.files


import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.workmanagertutorial.entity.IMAGE_LABELS
import com.example.workmanagertutorial.entity.LOCAL_FILE_URI
import com.example.workmanagertutorial.entity.REMOTE_FILE_URI
import kotlinx.coroutines.runBlocking


class FilesUploadWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    lateinit var remoteFiles: RemoteFiles

    override fun doWork(): Result {
        val path = inputData.getString(LOCAL_FILE_URI) ?: return Result.failure()
        val labels = inputData.getStringArray(IMAGE_LABELS)?.toList() ?: listOf()

        return try {
            runBlocking {
                val result = remoteFiles.upload(path, "uploads/${System.currentTimeMillis()}")
                Log.d(javaClass.simpleName, "start upload object")
                Result.success(workDataOf(REMOTE_FILE_URI to result, IMAGE_LABELS to labels.toTypedArray()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}