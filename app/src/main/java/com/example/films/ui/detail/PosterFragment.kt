package com.example.films.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.films.databinding.FragmentPosterBinding
import com.example.films.presentation.PosterViewModel
import com.example.films.util.BindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PosterFragment:BindingFragment<FragmentPosterBinding>() {

    private val PosterView: PosterViewModel by viewModel{
        parametersOf(requireArguments().getString(POSTER))
    }
    fun newInstance(poster:String) = PosterFragment().apply {
        arguments = Bundle().apply {
            putString(POSTER, poster)
        }
    }
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPosterBinding {
        return FragmentPosterBinding.inflate(inflater, container, false)
    }
    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        PosterView.observerState().observe(viewLifecycleOwner){
            if (it != null) {
                setUpImage(it)
            }
        }
    }
    private fun setUpImage(imageUrl:String){
        Glide.with(binding.poster)
            .load(imageUrl)
            .into(binding.poster)
    }

    companion object{
        private const val POSTER = "poster"
    }



}