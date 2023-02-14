package com.example.films

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class FilmsAdapter():RecyclerView.Adapter<FilmsViewHolder>() {

    var films = ArrayList<Films>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder = FilmsViewHolder(parent)



    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        holder.bind(films.get(position))
    }

    override fun getItemCount(): Int = films.size



}