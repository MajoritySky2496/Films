package com.example.films.domain.api

import android.os.Message
import com.example.films.data.dto.MovieDetailsResponse
import com.example.films.domain.models.Movie
import com.example.films.domain.models.MoviesDetail

interface MoviesInteractor {
    fun searchMovies(expression: String, consumer:MoviesConsumer)
    fun searchDetails(movieId:String, consumer: MoviesDetailConsumer)

    interface MoviesConsumer{
        fun consume (foundMovies:List<Movie>?, errorMessage: String?)
    }
    interface MoviesDetailConsumer{
        fun consumeDetails(foundDetail: MoviesDetail?, error:String?)
    }
}