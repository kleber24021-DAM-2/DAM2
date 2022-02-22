package com.quevedo.footballvideos.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.quevedo.footballvideos.FootballApp
import com.quevedo.footballvideos.ui.screens.main.components.MainAppBar
import com.quevedo.footballvideos.ui.screens.main.components.VideoList
import kotlinx.coroutines.flow.collect

@Composable
fun MainScreen(
    onNavigate: () -> Unit,
    viewModel: MainViewmodel
) {
    FootballApp {
        val uiState = viewModel.uiState.collectAsState()
        val scaffoldState = rememberScaffoldState()
        LaunchedEffect(key1 = true) {
            viewModel.uiState.collect { state ->
                state.error?.let {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = it
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.handleEvent(MainContract.Event.ErrorMostrado)
                    }
                }
            }
        }
        Scaffold(
            topBar = { MainAppBar() }
        ) { padding ->
            VideoList(
                videos = uiState.value.videosList ?: emptyList(),
                onClick = {
                    onNavigate()
                    viewModel.handleEvent(MainContract.Event.SelectVideo(it))
                },
                modifier = Modifier.padding(padding)
            )
        }
    }
}
