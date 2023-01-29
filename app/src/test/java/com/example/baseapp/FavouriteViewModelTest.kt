package com.example.baseapp

import com.example.baseapp.domain.model.vo.MovieResult
import com.example.baseapp.presentation.vm.FavoriteViewModel
import com.example.baseapp.repository.MovieRepositoryTest
import com.example.baseapp.usecase.FavouriteUseCaseTest
import com.example.baseapp.usecase.UpdateMovieUseCaseTest
import com.example.baseapp.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavouriteViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var useCaseTest: UpdateMovieUseCaseTest
    private lateinit var favouriteUseCaseTest: FavouriteUseCaseTest
    private val movieRepositoryTest = MovieRepositoryTest()

    @Before
    fun setup() {
        useCaseTest = UpdateMovieUseCaseTest()
        favouriteUseCaseTest = FavouriteUseCaseTest(movieRepositoryTest)
        favoriteViewModel = FavoriteViewModel(favouriteUseCaseTest, useCaseTest)
    }

    @Test
    fun view_model_get_favourites() {
        runTest {
            val job = launch(UnconfinedTestDispatcher()) {
                favoriteViewModel.favoriteMovies.collect()
            }
            movieRepositoryTest.send(
                listOf(
                    MovieResult(
                        id = 1,
                        title = "Tom y Jerry",
                        posterPath = "/tom_jerry",
                        releaseDate = "22/22/22",
                        overview = "Great movie!",
                        isFavourite = false
                    )
                )
            )
            Assert.assertTrue(favoriteViewModel.favoriteMovies.value.movies.orEmpty().isNotEmpty())
            job.cancel()
        }
    }

    @Test
    fun view_model_get_favourites_not_found() {
        runTest {
            val job = launch(UnconfinedTestDispatcher()) {
                favoriteViewModel.favoriteMovies.collect()
            }
            movieRepositoryTest.send(listOf())
            Assert.assertTrue(favoriteViewModel.favoriteMovies.value.movies.orEmpty().isEmpty())
            job.cancel()
        }
    }
}