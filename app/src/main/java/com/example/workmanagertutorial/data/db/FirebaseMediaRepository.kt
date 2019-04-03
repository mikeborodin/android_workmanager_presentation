package com.example.workmanagertutorial.data.db

import com.example.workmanagertutorial.data.await
import com.example.workmanagertutorial.entity.Media
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseMediaRepository : Repository {
    override suspend fun create(media: Media) {

        val ref = FirebaseFirestore.getInstance().collection("media").document()

        media.id = ref.id

        return ref.set(media).await()
    }
}