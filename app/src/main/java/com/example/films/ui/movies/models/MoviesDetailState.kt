package com.example.films.ui.movies.models

import android.os.Message
import com.example.films.domain.models.Movie
import com.example.films.domain.models.MoviesDetail

sealed interface MoviesDetailState{
    data class Content(
        val movies:MoviesDetail
    ):MoviesDetailState
    data class Error(val message: String):MoviesDetailState
}