package com.more.camerapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.more.camerapp.themes.CameraAppTheme
import com.more.camerapp.widgets.NavButton

@Composable
fun HomeScreen(
    onCameraClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            NavButton(onClick = onCameraClick, buttonText = "Start Camera")
            Spacer(modifier = Modifier.height(16.dp))
            NavButton(onClick = onSettingsClick, buttonText = "Settings")
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    CameraAppTheme {
        HomeScreen({}, {})
    }
}