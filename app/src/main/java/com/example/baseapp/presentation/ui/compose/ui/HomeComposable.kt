package com.example.baseapp.presentation.ui.compose.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baseapp.R
import com.example.baseapp.presentation.ui.compose.core.LoadingDialog
import com.example.baseapp.presentation.ui.compose.core.SnackBar
import com.example.baseapp.presentation.ui.compose.core.TextFieldComposable
import com.example.baseapp.presentation.ui.compose.theme.Blue
import com.example.baseapp.presentation.ui.compose.theme.GreenB
import com.example.baseapp.presentation.ui.compose.theme.LightGrey
import com.example.baseapp.presentation.vm.StateUi

@Composable
fun HomeComposable(stateUi: StateUi.ShowMovies) {
    val states = remember { mutableStateOf(stateUi) }
    SideEffect { Log.d("NB", "HomeComposableRecompositions") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GreenB)
    ) {
        states.value = stateUi
        val state = rememberLazyListState()
        if (stateUi.isLoading) LoadingDialog {}

        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .background(GreenB)
                    .padding(10.dp)
            ) {
                Column {
                    DismissText(
                        scrollProvider = { state.firstVisibleItemScrollOffset },
                        indexProvider = { state.firstVisibleItemIndex }
                    )
                    TextFieldComposable(
                        searchModel = { states.value.searchModel },
                        scrollProvider = { state.firstVisibleItemScrollOffset },
                        indexProvider = { state.firstVisibleItemIndex }
                    )
                }
            }
            if (stateUi.movies.isNotEmpty()) {
                LazyColumn(
                    state = state,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(10.dp)
                ) {
                    items(
                        items = stateUi.movies,
                        key = { cardModel -> cardModel.id }
                    ) { item ->
                        MovieCard { item }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(10.dp)
                )
            }
        }
        val snackBarModel = states.value.snackBarModel
        if (snackBarModel?.addOrRemoveMovie != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 10.dp)
            ) {
                SnackBar(
                    stringRes = if (snackBarModel.addOrRemoveMovie) {
                        R.string.pelicula_removida
                    } else {
                        R.string.pelicula_agregada
                    },
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

@Composable
fun DismissText(
    scrollProvider: () -> Int,
    indexProvider: () -> Int,
) {
    val s: Float = if (indexProvider() == 0) scrollProvider().toFloat() / 100F else 0F

    Text(
        text = "CineView 4.0",
        color = Color.White,
        fontSize = 30.sp,
        modifier = Modifier.graphicsLayer {
            alpha = 1 - s
        }
    )
}

/*
@Preview
@Composable
fun PreviewHomeComposable() {
    DesignTheme {
        HomeComposable {
            StateUi.ShowMovies(
                isLoading = false,
                movies = emptyList(),
                snackBarModel = null,
                searchModel = SearchModel(
                    errorResource = null,
                    titleResource = R.string.favoritos,
                    labelResource = R.string.favoritos,
                    validateResource = null,
                    enabled = true,
                    validateOrRemove = true,
                    searchText = null,
                    searchActionable = {},
                    acceptActionable = {}
                )
            )
        }
    }
}
*/