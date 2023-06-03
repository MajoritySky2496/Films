package com.example.films.presentation


import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.R
import com.example.films.domain.api.MoviesInteractor
import com.example.films.domain.models.Movie
import com.example.films.ui.movies.models.MoviesState
import com.example.films.domain.api.ResourceProvider

class MoviesSearchViewModel(private val interactor: MoviesInteractor ,private val resourceProvider: ResourceProvider):ViewModel() {

    private val handler = Handler(Looper.getMainLooper())



    private val stateLiveData = MutableLiveData<MoviesState>()
    fun observerState(): LiveData<MoviesState> = stateLiveData



    private val showToast = SingleLiveEvent<String>()
    fun observeShowToast(): LiveData<String> = showToast

    private var lastSearchText: String? = null

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

    private fun searchRequest(newSearchText:String) {
        if (newSearchText.isNotEmpty()) {
            renderState(MoviesState.Loading)

            interactor.searchMovies(
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
                                errorMessage  =resourceProvider.getString(R.string.something_went_wrong))

                            }
                            movies.isEmpty() ->{
                                MoviesState.Empty(
                                    message = resourceProvider.getString(R.string.nothing_found)
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


    }
}

