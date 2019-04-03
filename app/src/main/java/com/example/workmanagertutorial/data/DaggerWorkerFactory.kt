package com.example.workmanagertutorial.data;

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.workmanagertutorial.data.db.PostWorker
import com.example.workmanagertutorial.data.db.Repository
import com.example.workmanagertutorial.data.files.FilesUploadWorker
import com.example.workmanagertutorial.data.files.RemoteFiles
import com.example.workmanagertutorial.data.tags.ImageAnnotationWorker
import com.example.workmanagertutorial.data.tags.VisionHelper

class DaggerWorkerFactory(
    private val remoteFiles: RemoteFiles,
    private val visionHelper: VisionHelper,
    private val repository: Repository
) :
    WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        val workerKlass = Class.forName(workerClassName).asSubclass(Worker::class.java)
        val constructor = workerKlass.getDeclaredConstructor(
            Context::class.java, WorkerParameters::class.java
        )
        val instance = constructor.newInstance(appContext, workerParameters)
        when (instance) {
            is FilesUploadWorker -> {
                instance.remoteFiles = remoteFiles
            }

            is ImageAnnotationWorker -> {
                instance.vision = visionHelper
            }

            is PostWorker -> {
                instance.repository = repository
            }
        }
        return instance
    }

}