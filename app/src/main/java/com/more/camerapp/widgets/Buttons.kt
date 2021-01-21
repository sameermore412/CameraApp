package com.more.camerapp.widgets

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.more.camerapp.themes.CameraAppTheme

@Composable
fun NavButton(buttonText: String, onClick: () -> Unit) {
    Button(
        shape = MaterialTheme.shapes.medium,
        onClick = onClick) {
        Text(text = buttonText)
    }
}

@Preview
@Composable
fun PreviewNavButton() {
    CameraAppTheme {
        NavButton("Test", onClick = {})
    }
}