package com.example.films.domain.api

import com.example.films.domain.models.Movie
import com.example.films.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
}