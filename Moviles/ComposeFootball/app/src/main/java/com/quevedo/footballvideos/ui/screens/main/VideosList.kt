package com.quevedo.footballvideos.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.quevedo.footballvideos.domain.models.OwnFootballVideo

@Composable
fun VideoList(
    videos: List<OwnFootballVideo>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items(videos) { videos ->
            VideoRender(
                videoToShow = videos,
                onClick = { onClick() },
                modifier = modifier
            )
        }
    }
}


@Composable
fun VideoRender(
    videoToShow: OwnFootballVideo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick() }
    ) {
        Row {
            Image(
                painter = rememberImagePainter(
                    data = videoToShow.thumbnail
                ), contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(text = videoToShow.title)
                Text(text = videoToShow.competition)
            }
        }
    }
}