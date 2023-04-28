package com.example.films.util

import android.app.Application
import com.example.films.presentation.MoviesSearchPresenter

class MoviesApplication: Application() {
    var moviesSearchPresenter:MoviesSearchPresenter? = null
}