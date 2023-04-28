package com.example.films.ui.movies

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.films.domain.models.Movie


class MoviesAdapter(private var clickListener: MovieClickListener):RecyclerView.Adapter<MoviesViewHolder>() {

    var movies = ArrayList<Movie>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder = MoviesViewHolder(parent)



    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(movies.get(position))
        holder.itemView.setOnClickListener{clickListener.onMovieClick(movies.get(position))}
    }

    override fun getItemCount(): Int = movies.size

    fun interface MovieClickListener{
        fun onMovieClick(movie: Movie)
    }

}
