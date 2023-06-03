package com.example.films.domain.impl

import com.example.films.domain.api.MoviesInteractor
import com.example.films.domain.api.MoviesRepository
import com.example.films.domain.models.Movie
import com.example.films.util.Resource
import java.util.concurrent.Executors

class MoviesInteractorImpl(private var repository: MoviesRepository):MoviesInteractor {
    private val executor = Executors.newCachedThreadPool()
    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute{
            when(val resource =repository.searchMovies(expression)){
                is Resource.Success -> {consumer.consume(resource.data, null)}
                is Resource.Error -> {consumer.consume(null, resource.message)}
            }
        }
    }

    override fun searchDetails(movieId: String, consumer: MoviesInteractor.MoviesDetailConsumer) {
        executor.execute {
            when(val resource = repository.searchDetailMovies(movieId)){
                is Resource.Success -> {consumer.consumeDetails(resource.data, null)}
                is Resource.Error -> {consumer.consumeDetails(null, resource.message
                )}
            }
        }
    }


}