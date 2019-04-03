package com.example.workmanagertutorial.ui

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val loadingStatus: MediatorLiveData<Boolean> = MediatorLiveData()

    val errorLiveData: MutableLiveData<Any> = MutableLiveData()

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

}