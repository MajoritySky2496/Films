package com.example.films.presentation


import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.films.util.Creator
import com.example.films.R
import com.example.films.domain.api.MoviesInteractor
import com.example.films.domain.models.Movie
import com.example.films.ui.movies.MoviesAdapter
import com.example.films.ui.movies.models.MoviesState
import moxy.MvpPresenter

class MoviesSearchPresenter( private val context: Context):MvpPresenter<MoviesView>() {
    private val moviesInteractor = Creator.provideMoviesInteractor(context)
    private val handler = Handler(Looper.getMainLooper())




    private var lastSearchText: String? = null
    private val searchRunnable = Runnable {
        val newSearchText = lastSearchText ?: ""
        searchRequest(newSearchText)
    }

    fun searchDebounce(changedText:String) {
        if (lastSearchText == changedText){
            return
        }
        this.lastSearchText = changedText
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private val movies = ArrayList<Movie>()

    override fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }


    private fun searchRequest(newSearchText:String) {
        if (newSearchText.isNotEmpty()) {
            viewState.render(MoviesState.Loading)


            moviesInteractor.searchMovies(
                newSearchText,
                object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    handler.post {

                        if (foundMovies!=null) {
                            movies.addAll(foundMovies)


                        }
                        when{
                            errorMessage!=null ->{
                                renderState(MoviesState.Error( errorMessage = context.getString(R.string.something_went_wrong),
                                )
                                )

                            }
                            movies.isEmpty() ->{
                                renderState(MoviesState.Empty(
                                    message = context.getString(R.string.nothing_found)
                                ))

                            }
                            else -> {
                                renderState(MoviesState.Content(movies = movies))
                            }
                        }
                    }
                }
            })
        }
    }
    private fun renderState(state: MoviesState) {
        viewState.render(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}

