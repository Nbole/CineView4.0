package com.example.baseapp.presentation.vm

import androidx.annotation.IntegerRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.baseapp.R
import com.example.baseapp.presentation.ui.FavoriteFragment
import com.example.baseapp.presentation.ui.HomeFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.reflect.KClass

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _navState: MutableStateFlow<KClass<out Fragment>> =
        MutableStateFlow(HomeFragment::class)

    val navState: StateFlow<KClass<out Fragment>> = _navState

    fun setNavigation(@IntegerRes nav: Int) {
        _navState.value = if (nav == R.id.favourites) {
            FavoriteFragment::class
        } else {
            HomeFragment::class
        }
    }
}
