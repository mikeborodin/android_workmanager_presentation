package com.example.workmanagertutorial.di

import com.example.workmanagertutorial.di.fragment.MainActivityFragmentsModule
import com.example.workmanagertutorial.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module(
    includes = [ViewModelModule::class]
)
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [MainActivityFragmentsModule::class])
    abstract fun contributeMainActivity(): MainActivity
}