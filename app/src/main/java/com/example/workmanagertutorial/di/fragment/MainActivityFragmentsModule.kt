package com.example.workmanagertutorial.di.fragment

import com.example.workmanagertutorial.ui.fragment.BlankFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainActivityFragmentsModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): BlankFragment

}
