package com.example.films.presentation


import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.SystemClock
import android.widget.Toast
import androidx.constraintlayout.motion.utils.ViewState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.films.util.Creator
import com.example.films.R
import com.example.films.domain.api.MoviesInteractor
import com.example.films.domain.models.Movie
import com.example.films.ui.movies.MoviesAdapter
import com.example.films.ui.movies.models.MoviesState
import com.example.films.ui.movies.models.ToastState
import com.example.films.util.MoviesApplication
import moxy.MvpPresenter
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class MoviesSearchViewModel(application: Application):AndroidViewModel(application) {
    private val moviesInteractor = Creator.provideMoviesInteractor(getApplication<Application>())
    private val handler = Handler(Looper.getMainLooper())



    private val stateLiveData = MutableLiveData<MoviesState>()
    fun observerState(): LiveData<MoviesState> = stateLiveData

//    private val toastState = MutableLiveData<ToastState>(ToastState.None)
//    fun observeToastState():LiveData<ToastState> = toastState

    private val showToast = SingleLiveEvent<String>()
    fun observeShowToast(): LiveData<String> = showToast

    private var lastSearchText: String? = null

//    fun toastWasShown(){
//        toastState.value = ToastState.None
//    }

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }


    fun searchDebounce(changedText:String) {
        if (lastSearchText == changedText) {
            return
        }
        this.lastSearchText = changedText
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        val searchRunnable = Runnable {
            searchRequest(changedText)
        }
        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime

        )

    }
//    fun showToast(message: String){
//        toastState.postValue(message)
//    }

    private fun searchRequest(newSearchText:String) {
        if (newSearchText.isNotEmpty()) {
            renderState(MoviesState.Loading)

            moviesInteractor.searchMovies(
                newSearchText,
                object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {

                        val movies = ArrayList<Movie>()

                        if (foundMovies!=null) {
                            movies.addAll(foundMovies)


                        }
                        when{
                            errorMessage!=null ->{
                                MoviesState.Error(
                                errorMessage  =getApplication<Application>().getString(R.string.something_went_wrong))

                            }
                            movies.isEmpty() ->{
                                MoviesState.Empty(
                                    message = getApplication<Application>().getString(R.string.nothing_found)
                                )

                            }
                            else -> {
                                renderState(MoviesState.Content(movies = movies))
                            }
                        }
                    }

            })
        }
    }
    private fun renderState(state: MoviesState) {

        stateLiveData.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()

        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MoviesSearchViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }
}

