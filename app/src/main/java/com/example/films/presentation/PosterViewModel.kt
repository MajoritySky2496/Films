package com.example.films.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.films.ui.movies.models.MoviesDetailState
import com.example.films.ui.movies.models.MoviesState

class PosterViewModel(posterUrl:String?):ViewModel() {

    private val urlLiveData = MutableLiveData(posterUrl)
    fun observerState(): MutableLiveData<String?> =  urlLiveData



}