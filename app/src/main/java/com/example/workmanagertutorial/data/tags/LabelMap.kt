package com.example.workmanagertutorial.data.tags

import android.content.Context
import android.net.Uri
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


typealias LabelMap = HashMap<String, Float>

class VisionHelper(private val context: Context) {

    suspend fun generateLabelsForImage(uri: Uri): LabelMap {


        return suspendCoroutine { c ->
            val visionImage = FirebaseVisionImage.fromFilePath(context, uri)

            val options = FirebaseVisionLabelDetectorOptions.Builder()
                .setConfidenceThreshold(.5f).build()

            val detector = FirebaseVision.getInstance()
                .getVisionLabelDetector(options)

            detector.detectInImage(visionImage).addOnSuccessListener { list ->
                val result = hashMapOf<String, Float>()
                list.forEach { label ->
                    result[label.label] = label.confidence
                }
                c.resume(result)
            }.addOnFailureListener {
                c.resume(LabelMap())
            }
        }
    }
}