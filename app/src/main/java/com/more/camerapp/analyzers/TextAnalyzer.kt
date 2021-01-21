package com.more.camerapp.analyzers

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition

class TextAnalyzer : ImageAnalysis.Analyzer {
    val recognizer by lazy { TextRecognition.getClient() }

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    Log.i(TextAnalyzer::class.java.simpleName, visionText.text)
                    imageProxy.close()
                }
                .addOnFailureListener { e ->
                    Log.e(TextAnalyzer::class.java.simpleName, "Failed", e)
                    imageProxy.close()
                }
        }
    }
}