package com.example.workmanagertutorial.data.files

import java.io.File

interface RemoteFiles {

    suspend fun download(remotePath: String, localPath: String): String
    suspend fun upload(localPath: String, remotePath: String): String
}