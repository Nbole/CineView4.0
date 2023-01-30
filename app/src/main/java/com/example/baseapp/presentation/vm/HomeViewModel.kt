package com.example.baseapp.presentation.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseapp.R
import com.example.baseapp.domain.model.vo.DomainResponse
import com.example.baseapp.domain.usecase.SearchUseCase
import com.example.baseapp.domain.usecase.UpdateMovieUseCase
import com.example.baseapp.presentation.model.MovieCardModel
import com.example.baseapp.presentation.model.SearchModel
import com.example.baseapp.presentation.model.SnackBarModel
import com.example.baseapp.presentation.vm.StateUi.Error
import com.example.baseapp.presentation.vm.StateUi.ShowMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val updateMovieUseCase: UpdateMovieUseCase,
    private val searchUseCase: SearchUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _addOrRemoveMovie: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    private val _movies: MutableStateFlow<List<Int>> = MutableStateFlow(listOf())
    private val _queryText: MutableStateFlow<String> = MutableStateFlow("")
    private val _saveStateQuery = savedStateHandle.getStateFlow("query", "").map { it }

    private val query: Flow<String> =
        combine(_queryText, _saveStateQuery) { _queryText, stateHandler ->
            _queryText.ifEmpty { stateHandler }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val movies = query.flatMapLatest { searchUseCase.searchMovies(it) }

    val latestMovies: StateFlow<StateUi> =
        combine(
            movies,
            _addOrRemoveMovie,
            _searchText,
            _movies
        ) { response, addOrRemoveMovie, searchText, movies ->
            when (response) {
                is DomainResponse.Loading -> ShowMovies(
                    isLoading = true,
                    movies = response.data.orEmpty().map {
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
                        ) { id, isFavourite ->
                            setFavouriteId(id, isFavourite)
                        }
                    },
                    snackBarModel = SnackBarModel(addOrRemoveMovie = addOrRemoveMovie) {
                        _addOrRemoveMovie.value = null
                    },
                    searchModel = SearchModel(
                        errorResource = null,
                        titleResource = R.string.busqueda,
                        labelResource = R.string.busqueda,
                        validateResource = R.string.busqueda,
                        enabled = true,
                        validateOrRemove = true,
                        searchText = searchText,
                        searchActionable = { },
                        acceptActionable = { }
                    )
                )
                is DomainResponse.Success -> ShowMovies(
                    isLoading = false,
                    movies = response.data.map {
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
                        ) { id, isFavourite -> setFavouriteId(id, isFavourite) }
                    },
                    snackBarModel = SnackBarModel(addOrRemoveMovie = addOrRemoveMovie) {
                        _addOrRemoveMovie.value = null
                    },
                    searchModel = SearchModel(
                        errorResource = null,
                        titleResource = R.string.busqueda,
                        labelResource = R.string.busqueda,
                        validateResource = R.string.busqueda,
                        enabled = true,
                        validateOrRemove = true,
                        searchText = searchText,
                        searchActionable = {
                            _searchText.value = it
                        },
                        acceptActionable = {
                            _queryText.value = it
                            savedStateHandle["query"] = it
                        }
                    )
                )
                is DomainResponse.Error -> Error
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            ShowMovies(
                isLoading = false,
                movies = emptyList(),
                snackBarModel = null,
                searchModel = SearchModel(
                    errorResource = null,
                    titleResource = R.string.busqueda,
                    labelResource = R.string.busqueda,
                    validateResource = R.string.busqueda,
                    enabled = true,
                    validateOrRemove = true,
                    searchText = "",
                    searchActionable = {},
                    acceptActionable = {}
                )
            )
        )

    private fun setFavouriteId(id: Int, addOrRemoveMovie: Boolean) {
        viewModelScope.launch(Dispatchers.Default) {
            updateMovieUseCase.updateFavoriteMovie(id)
            _addOrRemoveMovie.value = addOrRemoveMovie
        }
    }
}

sealed class StateUi {
    data class ShowMovies(
        val isLoading: Boolean,
        val movies: List<MovieCardModel>,
        val snackBarModel: SnackBarModel?,
        val searchModel: SearchModel,
    ) : StateUi()

    object Error : StateUi()
}
