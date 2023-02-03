package com.example.baseapp.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.baseapp.databinding.FavoriteFragmentBinding
import com.example.baseapp.presentation.ui.compose.theme.DesignTheme
import com.example.baseapp.presentation.ui.compose.ui.FavouriteCompose
import com.example.baseapp.presentation.vm.FavoriteViewModel
import com.example.baseapp.presentation.vm.FavouriteStateUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private val viewModel: FavoriteViewModel by viewModels()

    private lateinit var binding: FavoriteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cviewListing.setContent {
            DesignTheme {
                val state = viewModel.favoriteMovies.collectAsState()
                if (state.value is FavouriteStateUi.ShowMovies) {
                    FavouriteCompose (state.value as FavouriteStateUi.ShowMovies)
                }
            }
        }
    }
}
