package com.example.workmanagertutorial.data

import android.app.Application
import androidx.work.WorkerFactory
import com.example.workmanagertutorial.data.db.FirebaseMediaRepository
import com.example.workmanagertutorial.data.db.Repository
import com.example.workmanagertutorial.data.files.FirebaseRemoteFiles
import com.example.workmanagertutorial.data.files.RemoteFiles
import com.example.workmanagertutorial.data.tags.VisionHelper
import com.google.firebase.FirebaseApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideInternalFirebaseApp(application: Application): FirebaseApp {
        return FirebaseApp.initializeApp(application)!!
    }

    @Provides
    fun remoteFiles(): RemoteFiles = FirebaseRemoteFiles()

    @Provides
    fun visionHelper(application: Application): VisionHelper = VisionHelper(application.applicationContext)

    @Provides
    fun repository(): Repository = FirebaseMediaRepository()

    @Provides
    @Singleton
    fun workerFactory(remoteFiles: RemoteFiles, visionHelper: VisionHelper, repository: Repository):
            WorkerFactory = DaggerWorkerFactory(remoteFiles, visionHelper, repository)

}
