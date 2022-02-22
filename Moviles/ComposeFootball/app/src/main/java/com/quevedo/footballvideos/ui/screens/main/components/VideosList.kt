package com.quevedo.footballvideos.ui.screens.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.quevedo.footballvideos.domain.models.OwnFootballVideo
import java.time.LocalDateTime

@Composable
fun VideoList(
    videos: List<OwnFootballVideo>,
    onClick: (selectedVideo : OwnFootballVideo) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items(videos) { videos ->
            VideoItem(
                videoToShow = videos,
                selectVideo = onClick,
                modifier = modifier
            )
        }
    }
}


@Composable
fun VideoItem(
    videoToShow: OwnFootballVideo,
    selectVideo: (selectedVideo : OwnFootballVideo) -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { selectVideo(videoToShow) }
    ) {
        val (image, title, league) = createRefs()
        Image(
            painter = rememberImagePainter(
                data = videoToShow.thumbnail
            ), contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .constrainAs(image) {
                    linkTo(
                        start = parent.start,
                        end = parent.end
                    )
                    width = Dimension.fillToConstraints
                }
        )
        Text(
            text = videoToShow.title,
            style = MaterialTheme.typography.h3.copy(fontSize = 18.sp),
            modifier = Modifier
                .constrainAs(title) {
                    linkTo(
                        start = image.start,
                        startMargin = 16.dp,
                        end = image.end,
                        endMargin = 16.dp
                    )
                    linkTo(
                        top = image.bottom,
                        topMargin = 8.dp,
                        bottom = league.top
                    )
                    width = Dimension.fillToConstraints
                }
        )
        Text(
            text = videoToShow.competition,
            style = MaterialTheme.typography.h6.copy(fontSize = 14.sp),
            modifier = Modifier
                .constrainAs(league) {
                    linkTo(
                        start = title.start,
                        end = title.end,
                    )
                    linkTo(
                        top = title.bottom,
                        bottom = parent.bottom
                    )
                    width = Dimension.fillToConstraints
                }
                .padding(bottom = 16.dp)
        )
    }
}