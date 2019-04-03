package com.example.workmanagertutorial.data.tags

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.workmanagertutorial.entity.IMAGE_LABELS
import com.example.workmanagertutorial.entity.LOCAL_FILE_URI
import kotlinx.coroutines.runBlocking


class ImageAnnotationWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    lateinit var vision: VisionHelper

    override fun doWork(): Result {

        val path = inputData.getString(LOCAL_FILE_URI) ?: return Result.failure()
        if (path.isEmpty()) return Result.failure()

        return try {
            val labels = runBlocking {
                vision.generateLabelsForImage(Uri.parse(path))
            }.keys.map {
                it.toLowerCase()
            }

            Log.d(javaClass.simpleName, "labels $labels")


            Result.success(workDataOf(IMAGE_LABELS to labels.toTypedArray(), LOCAL_FILE_URI to path))

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()

        }
    }


}

