package com.example.workmanagertutorial.data.db

import com.example.workmanagertutorial.entity.Media

interface Repository {

    suspend fun create(media: Media)
}