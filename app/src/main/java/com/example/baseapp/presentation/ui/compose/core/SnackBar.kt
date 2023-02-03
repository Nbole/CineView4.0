package com.example.baseapp.presentation.ui.compose.core

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.baseapp.R
import com.example.baseapp.presentation.model.SnackBarModel
import com.example.baseapp.presentation.ui.compose.theme.DarkDusk
import com.example.baseapp.presentation.ui.compose.theme.DesignTheme
import com.example.baseapp.presentation.ui.compose.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SnackBar(
    @StringRes stringRes: Int,
    @DrawableRes icon: Int,
    backgroundColor: Color,
    textColor: Color,
    iconColor: Color,
    horizontalPadding: Dp = 12.dp,
    bottomPadding: Dp = 0.dp,
    snackBarModel: SnackBarModel
) {
    var isVisible by remember { mutableStateOf(true) }
    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
        AnimatedVisibility(exit = fadeOut(), visible = isVisible) {
            LaunchedEffect(stringRes) {
                delay(2000)
                isVisible = false
                snackBarModel.action.invoke()
            }
            Surface(
                color = backgroundColor,
                elevation = 6.dp,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .padding(bottom = bottomPadding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NonClickableIcon(icon = icon, iconColor = iconColor)
                        Spacer(modifier = Modifier.width(4.dp))
                        Paragraphs.Paragraph(
                            stringRes = stringRes,
                            paragraphSize = Paragraphs.ParagraphSize.PARAGRAPH_14_SP,
                            color = textColor,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
internal fun ParentSnackBarPreview() {
    DesignTheme {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = {
                SnackbarHost(it) {
                    SnackBar(
                        stringRes = R.string.busqueda,
                        icon = R.drawable.ic_baseline_local_movies_24,
                        backgroundColor = DarkDusk,
                        textColor = White,
                        iconColor = White,
                        snackBarModel = SnackBarModel(false) {}
                    )
                }
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Show snackbar") },
                    onClick = {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("")
                        }
                    }
                )
            },
            content = { innerPadding ->
                Text(
                    text = "Custom Snackbar Demo",
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
        )
    }
}
