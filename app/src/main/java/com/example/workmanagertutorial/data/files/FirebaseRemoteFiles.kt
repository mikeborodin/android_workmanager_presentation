package com.example.workmanagertutorial.data.files

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseRemoteFiles : RemoteFiles {

    override suspend fun upload(localPath: String, remotePath: String): String =
        suspendCoroutine { c ->
            val ref = FirebaseStorage.getInstance().getReference(remotePath)
            ref.putFile(Uri.parse(localPath)).addOnSuccessListener { snap ->
                ref.downloadUrl.addOnSuccessListener {
                    c.resume(it.toString())
                }.addOnFailureListener {
                    c.resumeWithException(it)
                }
            }.addOnFailureListener {
                c.resumeWithException(it)
            }
        }

    override suspend fun download(remotePath: String, localPath: String): String =
        suspendCoroutine { c ->
            val ref = FirebaseStorage.getInstance().getReference(remotePath)
            ref.getFile(Uri.parse(localPath)).addOnSuccessListener { snap ->
                c.resume(localPath)
            }.addOnFailureListener {
                c.resumeWithException(it)
            }
        }
}