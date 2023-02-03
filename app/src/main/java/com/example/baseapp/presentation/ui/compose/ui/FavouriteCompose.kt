package com.example.baseapp.presentation.ui.compose.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baseapp.R
import com.example.baseapp.presentation.model.SnackBarModel
import com.example.baseapp.presentation.ui.compose.core.Paragraphs
import com.example.baseapp.presentation.ui.compose.core.SnackBar
import com.example.baseapp.presentation.ui.compose.core.TopBarComposable
import com.example.baseapp.presentation.ui.compose.theme.Blue
import com.example.baseapp.presentation.ui.compose.theme.DesignTheme
import com.example.baseapp.presentation.ui.compose.theme.LightGrey
import com.example.baseapp.presentation.ui.compose.theme.Yellow
import com.example.baseapp.presentation.vm.FavouriteStateUi

@Composable
fun FavouriteCompose(stateUi: FavouriteStateUi.ShowMovies) {
    val movieState = remember {
        mutableStateOf(stateUi)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        SideEffect { Log.d("NB", "FavouriteComposeRecomposable") }

        val state = rememberLazyListState()
        movieState.value = stateUi
        Column(
            Modifier
                .fillMaxSize()
                .background(color = Yellow)
        ) {
            TopBarComposable(
                { state.firstVisibleItemScrollOffset }
            ) {
                state.firstVisibleItemIndex
            }
            if (stateUi.movies.isNullOrEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White),
                ) {
                    Image(
                        alignment = Alignment.Center,
                        modifier = Modifier.size(200.dp),
                        painter = painterResource(
                            id = R.drawable.ic_baseline_local_movies_24
                        ),
                        contentDescription = ""
                    )
                    Paragraphs.Paragraph(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .align(Alignment.CenterHorizontally),
                        stringRes = R.string.add_more_movies,
                        paragraphSize = Paragraphs.ParagraphSize.PARAGRAPH_24_SP
                    )
                }
            } else {
                LazyColumn(
                    state = state,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(10.dp)
                ) {
                    items(
                        items = movieState.value.movies.orEmpty(),
                        key = { cardModel -> cardModel.id }
                    ) { items ->
                        MovieCard(items)
                    }
                }
            }
        }
        val snackBarModel = movieState.value.snackBarModel
        if (snackBarModel.addOrRemoveMovie == true) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 10.dp)
            ) {
                SnackBar(
                    stringRes = R.string.pelicula_removida,
                    icon = R.drawable.ic_baseline_local_movies_24,
                    backgroundColor = Color.Black,
                    textColor = LightGrey,
                    iconColor = Blue,
                    snackBarModel = snackBarModel
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewFavouriteCompose() {
    DesignTheme {
        FavouriteCompose(
            FavouriteStateUi.ShowMovies(movies = null, snackBarModel = SnackBarModel(null) {})
        )
    }
}
