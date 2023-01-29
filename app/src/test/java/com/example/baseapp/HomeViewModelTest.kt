package com.example.baseapp

import androidx.lifecycle.SavedStateHandle
import com.example.baseapp.domain.model.vo.DomainResponse
import com.example.baseapp.domain.model.vo.MovieResult
import com.example.baseapp.presentation.vm.HomeViewModel
import com.example.baseapp.presentation.vm.StateUi
import com.example.baseapp.repository.MovieRepositoryTest
import com.example.baseapp.usecase.SearchUseCaseTest
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
class HomeViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    private lateinit var useCaseTest: UpdateMovieUseCaseTest
    private lateinit var searchUseCaseTest: SearchUseCaseTest
    private lateinit var homeViewModel: HomeViewModel
    private val movieRepositoryTest = MovieRepositoryTest()

    @Before
    fun setUp() {
        useCaseTest = UpdateMovieUseCaseTest()
        searchUseCaseTest = SearchUseCaseTest(movieRepositoryTest)
        homeViewModel = HomeViewModel(useCaseTest, searchUseCaseTest, SavedStateHandle())
    }

    @Test
    fun view_model_success() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { homeViewModel.latestMovies.collect() }
        movieRepositoryTest.send(
            DomainResponse.Success(
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
        )
        Assert.assertTrue(homeViewModel.latestMovies.value is StateUi.ShowMovies)
        Assert.assertTrue(
            (homeViewModel.latestMovies.value as StateUi.ShowMovies).movies.isNotEmpty()
        )
        Assert.assertTrue(!(homeViewModel.latestMovies.value as StateUi.ShowMovies).isLoading)
        collectJob.cancel()
    }

    @Test
    fun view_model_failed() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { homeViewModel.latestMovies.collect() }
        movieRepositoryTest.send(
            DomainResponse.Error(
                "Error",
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
        )
        Assert.assertTrue(homeViewModel.latestMovies.value is StateUi.Error)
        collectJob.cancel()
    }

    @Test
    fun view_model_loading() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { homeViewModel.latestMovies.collect() }
        movieRepositoryTest.send(
            DomainResponse.Loading(
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
        )
        Assert.assertTrue(homeViewModel.latestMovies.value is StateUi.ShowMovies)
        Assert.assertTrue((homeViewModel.latestMovies.value as StateUi.ShowMovies).isLoading)
        collectJob.cancel()
    }
}
