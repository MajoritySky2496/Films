package com.example.films.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.domain.api.MoviesInteractor
import com.example.films.domain.models.MoviesDetail
import com.example.films.ui.movies.models.MoviesDetailState

class MoviesDetailViewModel(movieId:String, moviesInteractor:MoviesInteractor):ViewModel() {
    private val stateLiveData = MutableLiveData<MoviesDetailState>()
    fun observerState(): LiveData<MoviesDetailState> = stateLiveData
    init {
        moviesInteractor.searchDetails(
            movieId,
            object : MoviesInteractor.MoviesDetailConsumer {

                override fun consumeDetails(foundDetail: MoviesDetail?, error: String?) {
                    if (foundDetail !=null){
                        stateLiveData.postValue(MoviesDetailState.Content(foundDetail))
                    }
                    else{
                        stateLiveData.postValue(MoviesDetailState.Error(error ?: "Error"))

                    }
                }

            })
    }
}