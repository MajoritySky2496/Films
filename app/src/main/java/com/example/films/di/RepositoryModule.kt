package com.example.films.di

import com.example.films.data.network.MoviesRepositoryImpl
import com.example.films.domain.api.MoviesRepository
import org.koin.dsl.module

val repositoryModule = module{
    single<MoviesRepository> {
        MoviesRepositoryImpl(get())
    }
}