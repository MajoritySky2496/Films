package com.example.films.data.network

import com.example.films.R
import com.example.films.data.NetworkClient
import com.example.films.data.dto.MovieDetailsRequest
import com.example.films.data.dto.MovieDetailsResponse
import com.example.films.data.dto.MovieDto
import com.example.films.data.dto.MoviesSearchRequest
import com.example.films.data.dto.MoviesSearchResponse
import com.example.films.domain.api.MoviesRepository
import com.example.films.domain.models.Movie
import com.example.films.domain.models.MoviesDetail
import com.example.films.util.Resource

class MoviesRepositoryImpl(private val networkClient: NetworkClient):MoviesRepository{


    override fun searchMovies(expression: String):Resource<List<Movie>>  {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when(response.resultCode){
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 ->{
                with(response as MoviesSearchResponse) {
                    Resource.Success(results.map {
                        Movie(it.id, it.resultType, it.image, it.title, it.description)
                    })
                }

            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun searchDetailMovies(movieId:String): Resource<MoviesDetail> {
       val response = networkClient.doRequest(MovieDetailsRequest(movieId))
        return when(response.resultCode){
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200-> {
                with(response as MovieDetailsResponse){
                    Resource.Success(MoviesDetail(id, title, imDbRating, year,
                        countries, genres, directors, writers, stars, plot))
                }
            }
            else -> {

                Resource.Error(message = response.resultCode.toString())
            }
        }
    }

}