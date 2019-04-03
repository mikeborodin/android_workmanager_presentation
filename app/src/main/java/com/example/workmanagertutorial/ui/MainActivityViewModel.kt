package com.example.workmanagertutorial.ui


import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.work.*
import com.example.workmanagertutorial.data.db.PostWorker
import com.example.workmanagertutorial.data.files.FilesUploadWorker
import com.example.workmanagertutorial.data.tags.ImageAnnotationWorker
import com.example.workmanagertutorial.entity.LOCAL_FILE_URI
import com.example.workmanagertutorial.entity.REMOTE_FILE_URI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel @Inject constructor() : BaseViewModel(), CoroutineScope {

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    private val workId = MutableLiveData<String>()

    val status = MediatorLiveData<String>().apply {
        addSource(Transformations.switchMap(workId) {
            WorkManager.getInstance().getWorkInfosForUniqueWorkLiveData(it)
        }) {
            postValue(it.mapIndexed { i, it ->
                it.outputData.getString(REMOTE_FILE_URI)?.let {
                    image.postValue(it)
                }
                "$i ${it.state.name}"
            }.joinToString(separator = "\n"))
        }
    }

    val image = MutableLiveData<String>()


    fun setImage(uri: String) {
        image.value = uri
    }

    fun uploadPhoto() {
        val uri = image.value ?: return
        Log.d(javaClass.simpleName, "Starting upload of $uri")
        status.postValue("Uploading...")

        startUpload(uri)
    }

    private fun startUpload(uri: String) {
        val id = "test_" + System.currentTimeMillis()
        this.workId.value = id
        val operation = WorkManager.getInstance().beginUniqueWork(
            id, ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.Builder(ImageAnnotationWorker::class.java)
                .setInputData(workDataOf(LOCAL_FILE_URI to uri))
                .build()
        ).then(
            OneTimeWorkRequest.Builder(FilesUploadWorker::class.java)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
        ).then(
            OneTimeWorkRequest.Builder(PostWorker::class.java)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
        ).enqueue()


    }


}