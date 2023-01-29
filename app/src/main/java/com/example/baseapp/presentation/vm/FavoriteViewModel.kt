package com.example.baseapp.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseapp.domain.usecase.FavoriteUseCase
import com.example.baseapp.domain.usecase.UpdateMovieUseCase
import com.example.baseapp.presentation.model.MovieCardModel
import com.example.baseapp.presentation.model.SnackBarModel
import com.example.baseapp.presentation.vm.FavouriteStateUi.ShowMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    favoriteUseCase: FavoriteUseCase,
    private val updateMovieUseCase: UpdateMovieUseCase,
) : ViewModel() {
    private val _snackBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _movies: MutableStateFlow<List<Int>> = MutableStateFlow(listOf())

    val favoriteMovies: StateFlow<ShowMovies> =
        combine(
            _snackBar,
            favoriteUseCase.getFavorites(),
            _movies
        ) { snackBar, result, movies ->
            ShowMovies(
                movies = result.map {
                    MovieCardModel(
                        id = it.id,
                        text = it.title,
                        poster = it.posterPath,
                        isFavourite = it.isFavourite,
                        releaseDate = it.releaseDate,
                        overView = it.overview,
                        mustShowDetail = movies.contains(it.id),
                        showDetail = { id ->
                            _movies.value = if (movies.contains(id)) {
                                movies.filter { v -> v != id }
                            } else {
                                movies.plus(id)
                            }
                        }
                    ) { id, _ ->
                        setFavouriteId(id)
                        _snackBar.value = true
                    }
                },
                snackBarModel = SnackBarModel(snackBar) {
                    _snackBar.value = false
                }
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            ShowMovies(movies = null, snackBarModel = SnackBarModel(false) {})
        )

    private fun setFavouriteId(id: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            updateMovieUseCase.updateFavoriteMovie(id)
        }
    }
}

sealed class FavouriteStateUi {
    data class ShowMovies(
        val movies: List<MovieCardModel>?,
        val snackBarModel: SnackBarModel,
    ) : FavouriteStateUi()
}
