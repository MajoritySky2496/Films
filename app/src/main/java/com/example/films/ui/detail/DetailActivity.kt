package com.example.films.ui.detail

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.films.R
import com.example.films.databinding.ActivityDetailsBinding
import com.example.films.presentation.MoviesDetailViewModel
import com.example.films.presentation.PosterView
import com.example.films.ui.DetailsViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity:AppCompatActivity() {
    private lateinit var binding:ActivityDetailsBinding
    private lateinit var tabMediator: TabLayoutMediator
    lateinit var movieId:String
    lateinit var imageUrl:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

         imageUrl = intent.extras?.getString("poster", "") ?: ""
        movieId = intent.extras?.getString("id","")?: ""

        binding.viewPager.adapter = DetailsViewPagerAdapter(supportFragmentManager, lifecycle, imageUrl, movieId)
        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager){
            tab, position ->
            when(position){
                0 -> tab.text = getString(R.string.poster)
                1 -> tab.text = getString(R.string.details)
            }

        }
        tabMediator.attach()

    }
    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }

}