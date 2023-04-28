package com.example.films.ui.poster

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.films.util.Creator
import com.example.films.R
import com.example.films.presentation.PosterView

class PosterActivity:Activity(), PosterView {
    private lateinit var poster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         var imageUrl = intent.extras?.getString("poster", "") ?: ""
        val posterController = Creator.providePosterPresenter(this,  imageUrl)
        setContentView(R.layout.activity_poster)
        poster = findViewById(R.id.poster)
        posterController.onCreate()

    }
    override fun setupPosterImage(url: String) {
        Glide.with(applicationContext)
            .load(url)
            .into(poster)
    }


}