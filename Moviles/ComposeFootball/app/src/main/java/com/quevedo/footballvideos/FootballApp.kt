package com.quevedo.footballvideos

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.quevedo.footballvideos.ui.theme.FootballVideosTheme

@Composable
fun FootballApp(content: @Composable () -> Unit) {
    FootballVideosTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}