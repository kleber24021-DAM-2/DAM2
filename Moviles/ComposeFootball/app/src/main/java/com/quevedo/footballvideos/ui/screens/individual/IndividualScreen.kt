package com.quevedo.footballvideos.ui.screens.individual

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.quevedo.footballvideos.FootballApp
import com.quevedo.footballvideos.domain.models.OwnFootballVideo
import com.quevedo.footballvideos.ui.screens.main.MainContract
import com.quevedo.footballvideos.ui.screens.main.MainViewmodel
import com.quevedo.footballvideos.ui.screens.main.components.MainAppBar
import kotlinx.coroutines.flow.collect

@Composable
fun IndividualScreen(
    viewModel: MainViewmodel
) {
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
    FootballApp {
        Scaffold(topBar = { MainAppBar() }) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
            ) {
                TopTitle(selectedVideo = uiState.value.selectedVideo!!)
                VideoPlayer(selectedVideo = uiState.value.selectedVideo!!)
            }
        }
    }
}

@Composable
fun TopTitle(
    selectedVideo: OwnFootballVideo
) {
    Text(
        text = selectedVideo.title,
        style = MaterialTheme.typography.h2.copy(fontSize = 18.sp),
        maxLines = 1,
    )
    Text(
        text = selectedVideo.competition,
        style = MaterialTheme.typography.h6.copy(fontSize = 14.sp)
    )
}

@Composable
fun VideoPlayer(
    selectedVideo: OwnFootballVideo
) {
    val context = LocalContext.current

    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(context)
            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(selectedVideo.video)))
            this.prepare(source)
        }
    }

    AndroidView(
        factory = {
            PlayerView(it).apply {
                exoPlayer.playWhenReady = true
                player = exoPlayer
            }
        }
    )
}