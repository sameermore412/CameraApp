package com.more.camerapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.more.camerapp.screens.AppScreenContainer
import com.more.camerapp.screens.CameraScreen
import com.more.camerapp.screens.HomeScreen
import com.more.camerapp.themes.CameraAppTheme

const val CAMERA_REQUEST_CODE = 100

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppScreenContainer(window=this.window) {
                HomeScreen(
                    onCameraClick = {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
                        } else {
                            this.startActivity(Intent(this, CameraActivity::class.java))
                        }
                    },
                    onSettingsClick = {

                    }
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.startActivity(Intent(this, CameraActivity::class.java))
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainContainer() {
    AppScreenContainer { HomeScreen({}, {}) }
}