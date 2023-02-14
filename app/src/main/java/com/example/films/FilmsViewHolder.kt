package com.example.films

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FilmsViewHolder(parent: ViewGroup) :RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
    .inflate(R.layout.fims_list, parent
, false)){
    var image: ImageView = itemView.findViewById(R.id.image)
    var title: TextView = itemView.findViewById(R.id.name)
    var description: TextView = itemView.findViewById(R.id.description)

    fun bind(films: Films) {
        title.text = films.title
        description.text = films.description


        Glide.with(itemView).load(films.image).centerInside().into(image)

    }
}