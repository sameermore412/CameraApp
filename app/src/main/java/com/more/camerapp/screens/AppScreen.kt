package com.more.camerapp.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.view.Window
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.more.camerapp.themes.CameraAppTheme

@Composable
fun AppScreenContainer(
    showAppBar: Boolean = true,
    window: Window? = null,
    bodyContent: @Composable (PaddingValues) -> Unit
) {
    CameraAppTheme(window) {
        Scaffold(
            topBar = { CameraAppBar(showAppBar) },
            bodyContent = bodyContent

        )
    }
}

@Composable
fun CameraAppBar(showAppBar: Boolean) {
    if (showAppBar) {
        TopAppBar(title = { Text(text = "Camera App") })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppScreenContainer() {
    CameraAppTheme {
        Scaffold(
            bodyContent = {}
        )
    }
}