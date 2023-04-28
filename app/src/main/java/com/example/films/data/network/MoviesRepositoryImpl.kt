package com.example.films.data.network

import com.example.films.R
import com.example.films.data.NetworkClient
import com.example.films.data.dto.MovieDto
import com.example.films.data.dto.MoviesSearchRequest
import com.example.films.data.dto.MoviesSearchResponse
import com.example.films.domain.api.MoviesRepository
import com.example.films.domain.models.Movie
import com.example.films.util.Resource

class MoviesRepositoryImpl(private val networkClient: NetworkClient):MoviesRepository{


    override fun searchMovies(expression: String):Resource<List<Movie>>  {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when(response.resultCode){
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 ->{
                if((response as MoviesSearchResponse).results.isNotEmpty() == true){
                    Resource.Success((response as MoviesSearchResponse).results.map {
                        Movie(it.id, it.resultType, it.image, it.title, it.description)
                    })
                }else{
                    Resource.Success((response as MoviesSearchResponse).results.map {
                        Movie(it.id, it.resultType, it.image, it.title, it.description)
                    })
                    Resource.Error("Ошибка ")

                }
//
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

}