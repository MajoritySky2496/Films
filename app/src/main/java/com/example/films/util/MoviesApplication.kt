package com.example.films.util

import android.app.Application
import com.example.films.presentation.MoviesSearchViewModel

class MoviesApplication: Application() {
    var moviesSearchViewModel:MoviesSearchViewModel? = null
}