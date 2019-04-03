package com.example.workmanagertutorial.ui

import android.app.Activity
import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.workmanagertutorial.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {
    override fun activityInjector() = dispatchingAndroidInjector

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    @Inject
    lateinit var workerFactory: WorkerFactory

    override fun onCreate() {
        super.onCreate()
        initDagger()
        initWorkManager()
    }

    private fun initDagger() {
        DaggerAppComponent.builder().application(this)
            .build().inject(this)
    }

    private fun initWorkManager() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory) // Overrides default WorkerFactory
            .build()
        WorkManager.initialize(this, config)
    }
}