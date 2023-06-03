package com.example.films.di

import com.example.films.presentation.MoviesDetailViewModel
import com.example.films.presentation.MoviesSearchViewModel
import com.example.films.presentation.PosterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module{

    viewModel{(moviesId:String) ->
        MoviesDetailViewModel(moviesId, get())
    }
    viewModel{
        MoviesSearchViewModel(get(), get())
    }
    viewModel {(posterUrl:String) ->
        PosterViewModel(posterUrl)
    }
}