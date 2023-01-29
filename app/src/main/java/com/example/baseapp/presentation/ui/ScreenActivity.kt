package com.example.baseapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.baseapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
       installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
