package com.example.baseapp

import com.example.baseapp.presentation.ui.HomeFragment
import com.example.baseapp.presentation.vm.MainViewModel
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
class MainViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel()
    }

    @Test
    fun test_viewModel_idle() = runTest {
        Assert.assertEquals(HomeFragment::class, viewModel.navState.value)
    }

    @Test
    fun test_viewModel_nav() = runTest {
        viewModel.setNavigation(R.id.home)
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.navState.collect() }
        Assert.assertEquals(HomeFragment::class, viewModel.navState.value)
        collectJob.cancel()
    }
}
