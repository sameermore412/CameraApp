package com.more.camerapp.themes

import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.AmbientContext


private val primaryColor = Color(0xff5e92f3)
private val primaryLightColor = Color(0xff1565c0)
private val primaryDarkColor = Color(0xff003c8f)
private val secondaryColor = Color(0xff3949ab)
private val secondaryLightColor = Color(0xff6f74dd)
private val secondaryDarkColor = Color(0xff00227b)

private val DarkColors = darkColors(
    primary = primaryColor,
    primaryVariant = primaryDarkColor,
    secondary = secondaryDarkColor
)
private val LightColors = lightColors(
    primary = primaryColor,
    primaryVariant = primaryLightColor,
    secondary = secondaryLightColor,
)

@Composable
fun CameraAppTheme(window: Window? = null, content: @Composable () -> Unit) {
    //val colors = if (!isSystemInDarkTheme()) DarkColors else LightColors
    MaterialTheme(colors = LightColors) {
        window?.statusBarColor = MaterialTheme.colors.primaryVariant.toArgb()
        window?.navigationBarColor = MaterialTheme.colors.primaryVariant.toArgb()
        content()
    }
}