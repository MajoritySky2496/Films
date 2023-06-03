package com.example.films.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.films.databinding.FragmentAboutBinding
import com.example.films.domain.models.MoviesDetail
import com.example.films.presentation.MoviesDetailViewModel
import com.example.films.ui.movies.models.MoviesDetailState
import com.example.films.util.BindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AboutFragment:BindingFragment<FragmentAboutBinding>() {

    private val MoviesDetailViewModel: MoviesDetailViewModel by viewModel{
        parametersOf(requireArguments().getString(MOVIE_ID))
    }

    fun newInstance(movieId:String) = AboutFragment().apply {
        arguments = Bundle().apply {
            putString(MOVIE_ID, movieId)
        }
    }
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAboutBinding {
        return FragmentAboutBinding.inflate(inflater, container, false)
    }
    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        MoviesDetailViewModel.observerState().observe(viewLifecycleOwner){
            state(it)
        }



    }
    fun initViews(movieDetails: MoviesDetail){
        binding.apply {
            details.visibility = View.VISIBLE
            errorMessage.visibility = View.GONE
            title.text = movieDetails.title
            ratingValue.text = movieDetails.imDbRating
            yearValue.text = movieDetails.year
            countryValue.text = movieDetails.countries
            genreValue.text = movieDetails.genres
            directorValue.text = movieDetails.directors
            writerValue.text = movieDetails.writers
            castValue.text = movieDetails.stars
            plot.text = movieDetails.plot
        }

    }
    private fun showErrorMessage(message: String) {
        binding.apply {
            details.visibility = View.GONE
            errorMessage.visibility = View.VISIBLE
            errorMessage.text = message
        }
    }
    fun state(state:MoviesDetailState){
        when(state){
            is MoviesDetailState.Content -> initViews(state.movies)
            is MoviesDetailState.Error -> showErrorMessage(state.message)
        }
    }
    companion object{
        private const val MOVIE_ID = "movie_id"
    }

}