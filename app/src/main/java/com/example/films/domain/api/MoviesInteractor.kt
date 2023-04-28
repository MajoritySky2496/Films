package com.example.films.domain.api

import android.os.Message
import com.example.films.domain.models.Movie

interface MoviesInteractor {
    fun searchMovies(expression: String, consumer:MoviesConsumer)

    interface MoviesConsumer{
        fun consume (foundMovies:List<Movie>?, errorMessage: String?)
    }
}