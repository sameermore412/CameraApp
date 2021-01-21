package com.more.camerapp.screens

import android.util.Size
import androidx.camera.camera2.internal.annotation.CameraExecutor
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.AmbientLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.more.camerapp.analyzers.BaseAnalyzer
import com.more.camerapp.analyzers.FaceAnalyzer
import com.more.camerapp.views.FacePointsView
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.camera.core.Preview as CameraPreview

@Composable
fun CameraScreen(analyzer: ImageAnalysis.Analyzer) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        // Create references for the composables to constrain
        val (cameraPreview, controls) = createRefs()

        CameraPreview(Modifier.constrainAs(cameraPreview) {
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(controls.top) },
            analyzer
        )

        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.background(Color.Green)
                .constrainAs(controls) {
                    width = Dimension.fillToConstraints
                    height = Dimension.percent(.25f)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom) }
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "HI")
            }
        }
    }
}

@Composable
fun CameraPreview(modifier: Modifier, analyzer: ImageAnalysis.Analyzer) {
    val lifecycleOwner = AmbientLifecycleOwner.current
    val context = AmbientContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }
    val customView = remember {
        PreviewView(context)
    }

    val faceView = remember {
        FacePointsView(context).apply {
            if (analyzer is FaceAnalyzer) {
                analyzer.faceView = this
            }
        }
    }

    Box(modifier = Modifier.background(Color.Blue)) {
        AndroidView(modifier = Modifier.fillMaxSize(), viewBlock = { customView }) {
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                bind(
                    lifecycleOwner,
                    it,
                    cameraProvider, cameraExecutor, analyzer)
            }, ContextCompat.getMainExecutor(context))
        }
        AndroidView(modifier = Modifier.fillMaxSize(), viewBlock = { faceView })
    }
}

fun bind(
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    cameraProvider: ProcessCameraProvider,
    cameraExecutor: Executor,
    analyzer: ImageAnalysis.Analyzer
) {
    val preview: CameraPreview = CameraPreview.Builder().build()

    val cameraSelector: CameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    preview.setSurfaceProvider(previewView.surfaceProvider)

    val imageAnalysis = ImageAnalysis.Builder()
        .setTargetResolution(Size(1280, 720))
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
    imageAnalysis.setAnalyzer(cameraExecutor, analyzer)

    cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)
}
@Preview
@Composable
fun PreviewCameraScreen() {
    CameraScreen(BaseAnalyzer())
}