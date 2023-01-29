package com.example.baseapp.data.remote.model

import com.example.baseapp.data.local.model.db.Movie
import kotlinx.serialization.Serializable

@Serializable
data class MovieResult(val results: List<Movie>)
