package com.more.camerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.more.camerapp.analyzers.FaceAnalyzer
import com.more.camerapp.analyzers.TextAnalyzer
import com.more.camerapp.screens.AppScreenContainer
import com.more.camerapp.screens.CameraScreen

class CameraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppScreenContainer(showAppBar = false, this.window) { CameraScreen(FaceAnalyzer()) }
        }
    }
}