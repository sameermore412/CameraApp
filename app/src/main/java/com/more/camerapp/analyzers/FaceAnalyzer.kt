package com.more.camerapp.analyzers

import android.annotation.SuppressLint
import android.graphics.PointF
import android.graphics.Rect
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.more.camerapp.views.FacePointsView

class FaceAnalyzer: ImageAnalysis.Analyzer {
    // Real-time contour detection of multiple faces
    val realTimeOpts by lazy {
        FaceDetectorOptions.Builder()
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()
    }

    val detector by lazy { FaceDetection.getClient(realTimeOpts) }

    var faceView: FacePointsView? = null

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            detector.process(image)
                .addOnSuccessListener {
                    val contours = mutableListOf<PointF>()
                    var boundingBox = Rect()
                    Log.i(FaceAnalyzer::class.java.simpleName, "Got a face")
                    for (face in it) {
                        contours += face.allContours.flatMap { it.points }.map { PointF(it.x, it.y) }
                        boundingBox = face.boundingBox
                    }
                    faceView?.apply {
                        if(contours.size > 0) {
                            this.onUpdatePoints(contours, boundingBox)
                        }
                    }

                    imageProxy.close()
                }.addOnFailureListener {
                    Log.e(FaceAnalyzer::class.java.simpleName, it.message, it)
                    imageProxy.close()
                }
        }
    }
}