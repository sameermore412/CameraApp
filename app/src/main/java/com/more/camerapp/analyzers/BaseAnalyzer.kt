package com.more.camerapp.analyzers

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

class BaseAnalyzer : ImageAnalysis.Analyzer {
    override fun analyze(image: ImageProxy) {
        Log.i(BaseAnalyzer::class.java.simpleName, image.toString())
        image.planes
        image.close()
    }
}