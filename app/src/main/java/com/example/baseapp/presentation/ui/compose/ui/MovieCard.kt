package com.example.baseapp.presentation.ui.compose.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baseapp.R
import com.example.baseapp.presentation.model.MovieCardModel
import com.example.baseapp.presentation.ui.compose.core.ClickableIcon
import com.example.baseapp.presentation.ui.compose.core.bounceClick
import com.example.baseapp.presentation.ui.compose.theme.DesignTheme

@Composable
fun MovieCard(movieCardModel: () -> MovieCardModel) {
    val showDetail = movieCardModel.invoke().mustShowDetail
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 5.dp)
            .clickable { movieCardModel.invoke().showDetail.invoke(movieCardModel.invoke().id) }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            val animatedDp = animateDpAsState(
                targetValue = if (movieCardModel.invoke().mustShowDetail) {
                    200.dp
                } else {
                    100.dp
                }
            )
            ImageFromUrl(
                url = movieCardModel().poster,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(animatedDp.value)
                    .padding(10.dp)
                    .weight(0.5F)
                    .align(Alignment.CenterVertically),
            )
            Column(
                modifier = Modifier.weight(1F),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = movieCardModel().text.orEmpty(),
                    modifier = Modifier.padding(top = 10.dp),
                    maxLines = 2,
                )
                Text(
                    text = movieCardModel().releaseDate.toString(),
                    modifier = Modifier.padding(top = 10.dp)
                )
                AnimatedVisibility(visible = showDetail) {
                    Column {
                        Text(
                            text = movieCardModel().overView.toString(),
                            modifier = Modifier.padding(top = 10.dp),
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }

            ClickableIcon(
                modifier = Modifier.padding(top = 10.dp, end = 10.dp).bounceClick(true),
                icon = if (movieCardModel().isFavourite) {
                    R.drawable.ic_baseline_favorite_border_24
                } else {
                    R.drawable.ic_baseline_favorite_border_24
                },
                iconColor = null,
                contentDescription = null
            ) { movieCardModel().actionable.invoke(movieCardModel().id, true) }
        }
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    DesignTheme {
        MovieCard {
            MovieCardModel(
                id = 1,
                text = null,
                poster = null,
                releaseDate = null,
                overView = null,
                isFavourite = false,
                mustShowDetail = false,
                showDetail = {},
                actionable = { _, _ -> }
            )
        }
    }
}
