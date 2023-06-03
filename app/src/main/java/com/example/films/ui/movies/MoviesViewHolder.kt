package com.example.films.ui.movies

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.films.R
import com.example.films.domain.models.Movie

class MoviesViewHolder(parent: ViewGroup) :RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
    .inflate(
        R.layout.list_item_movie, parent
, false)){
    var cover: ImageView = itemView.findViewById(R.id.cover)
    var title: TextView = itemView.findViewById(R.id.title)
    var description: TextView = itemView.findViewById(R.id.description)


    fun bind(movie: Movie) {
        title.text = movie.title
        description.text = movie.description


        Glide.with(itemView).load(movie.image).centerInside().into(cover)



    }

}