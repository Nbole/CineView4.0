package com.example.baseapp.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baseapp.R
import com.example.baseapp.databinding.ActivityMainBinding
import com.example.baseapp.presentation.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.home
            supportFragmentManager.commit { replace(R.id.container, HomeFragment()) }

            binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
                viewModel.setNavigation(menuItem.itemId)
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.CREATED) {
                        viewModel.navState.collect { navTo(it) }
                    }
                }
                true
            }
        }
    }

    private fun navTo(fragment: KClass<out Fragment>) {
        supportFragmentManager.fragments.takeIf {
            supportFragmentManager.fragments.find { it::class == fragment } == null
        }?.let {
            supportFragmentManager.commit {
                replace(
                    R.id.container,
                    if (fragment == HomeFragment::class) {
                        HomeFragment()
                    } else {
                        FavoriteFragment()
                    }
                )
            }
        }
    }
}
