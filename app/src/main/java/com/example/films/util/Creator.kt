package com.example.films.util

import android.content.Context
import com.example.films.data.network.MoviesRepositoryImpl
import com.example.films.data.network.RetrofitNetworkClient
import com.example.films.domain.api.MoviesInteractor
import com.example.films.domain.api.MoviesRepository
import com.example.films.domain.impl.MoviesInteractorImpl
import com.example.films.presentation.MoviesSearchViewModel
import com.example.films.presentation.PosterPresenter
import com.example.films.presentation.PosterView

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

//    fun provideMoviesSearchPresenter(
//        context: Context
//    ): MoviesSearchViewModel {
//        return MoviesSearchViewModel(
//            context = context
//        )
//    }

    fun providePosterPresenter(
        posterView: PosterView,
        url: String

    ): PosterPresenter {
        return PosterPresenter(
            posterView,
             url
        )
    }


}