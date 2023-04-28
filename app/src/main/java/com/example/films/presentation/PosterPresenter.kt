package com.example.films.presentation

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.films.R

class PosterPresenter( private val view: PosterView, private var imageUrl:String) {

    fun onCreate() {


        view.setupPosterImage(imageUrl)


    }
}
