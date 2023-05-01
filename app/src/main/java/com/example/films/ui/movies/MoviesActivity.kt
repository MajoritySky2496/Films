package com.example.films.ui.movies

import android.app.Activity
import android.content.Intent

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.films.util.Creator
import com.example.films.R
import com.example.films.domain.models.Movie
import com.example.films.presentation.MoviesSearchPresenter
import com.example.films.presentation.MoviesView
import com.example.films.ui.movies.models.MoviesState
import com.example.films.ui.poster.PosterActivity
import com.example.films.util.MoviesApplication
import moxy.MvpActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class MoviesActivity : MvpActivity(), MoviesView {

    lateinit var queryInput: EditText
    lateinit var placeholderMessage: TextView
    lateinit var moviesList: RecyclerView
    lateinit var progressBar: ProgressBar
    private var textWatcher: TextWatcher? = null


    private val adapter = MoviesAdapter {
        if (clickDebounce()) {
            val intent = Intent(this, PosterActivity::class.java)
            intent.putExtra("poster", it.image)
            startActivity(intent)
        }
    }


    private var isClickAllowed = true

    private val handler = Handler(Looper.getMainLooper())

    @InjectPresenter
    lateinit var moviesSearchPresenter: MoviesSearchPresenter

    @ProvidePresenter
    fun providePresenter(): MoviesSearchPresenter {
        return Creator.provideMoviesSearchPresenter(
            context = this.applicationContext,
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        queryInput = findViewById<EditText>(R.id.queryInput)
        placeholderMessage = findViewById<TextView>(R.id.placeholderMessage)
        moviesList = findViewById<RecyclerView>(R.id.locations)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        moviesList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        moviesList.adapter = adapter






        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                moviesSearchPresenter.searchDebounce(changedText = s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }
        textWatcher?.let {
            queryInput.addTextChangedListener(it)


        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    override fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Loading -> showLoading()
            is MoviesState.Content -> showContent(state.movies)
            is MoviesState.Error -> showError(state.errorMessage)
            is MoviesState.Empty -> showEmpty(state.message)
        }
    }

    fun showLoading() {
        moviesList.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    fun showError(errorMessage: String) {
        moviesList.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        placeholderMessage.text = errorMessage
    }

    fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)
    }

    fun showContent(movies: List<Movie>) {
        moviesList.visibility = View.VISIBLE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.GONE

        adapter.movies.clear()
        adapter.movies.addAll(movies)
        adapter.notifyDataSetChanged()
    }

    override fun makeToast(additionalMessage: String) {
        Toast.makeText(this, additionalMessage, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

}