package com.example.films.di

import com.example.films.domain.api.MoviesInteractor
import com.example.films.domain.impl.MoviesInteractorImpl
import org.koin.dsl.module

val interactorModule = module{

    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }

}