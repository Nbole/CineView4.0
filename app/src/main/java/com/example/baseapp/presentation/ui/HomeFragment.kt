package com.example.baseapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.baseapp.databinding.HomeFragmentBinding
import com.example.baseapp.presentation.ui.compose.theme.DesignTheme
import com.example.baseapp.presentation.ui.compose.ui.HomeComposable
import com.example.baseapp.presentation.vm.HomeViewModel
import com.example.baseapp.presentation.vm.StateUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cviewListing.setContent {
            val stateUi = viewModel.latestMovies.collectAsState().value
            DesignTheme {
                if (stateUi is StateUi.ShowMovies) {
                    HomeComposable (stateUi)
                }
            }
        }
    }
}