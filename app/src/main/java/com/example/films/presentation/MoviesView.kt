package com.example.films.presentation

import com.example.films.domain.models.Movie
import com.example.films.ui.movies.models.MoviesState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MoviesView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(state: MoviesState)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun makeToast(additionalMessage: String)
}