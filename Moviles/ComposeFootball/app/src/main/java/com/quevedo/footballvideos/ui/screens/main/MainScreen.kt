package com.quevedo.footballvideos.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.quevedo.footballvideos.FootballApp

@Composable
fun MainScreen(
    onNavigate: () -> Unit,
    viewModel: MainViewmodel
) {
    FootballApp {
        Scaffold(
            topBar = { MainAppBar() }
        ) { padding ->

            VideoList(
                videos = viewModel.uiState.value.videosList ?: emptyList(),
                onClick = { onNavigate() },
                modifier = Modifier.padding(padding)
            )
        }
    }
}
