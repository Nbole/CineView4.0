package com.example.baseapp.presentation.ui.compose.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.baseapp.BuildConfig

@Composable
fun ImageFromUrl(
    url: String?,
    modifier: Modifier = Modifier,
    @DrawableRes placeholderResId: Int? = null,
    contentScale: ContentScale = ContentScale.Fit,
) {
    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        val painter: ImagePainter = rememberImagePainter(
            BuildConfig.BASE_URL_IMAGE + url,
            builder = { placeholderResId?.let { placeholder(it) } }
        )
        Image(
            modifier = modifier.fillMaxSize(),
            contentScale = contentScale,
            painter = painter,
            contentDescription = "",
        )
    }
}
