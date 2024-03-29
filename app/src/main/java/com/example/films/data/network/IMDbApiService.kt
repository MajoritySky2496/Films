package com.example.films.data.network

import com.example.films.data.dto.MovieDetailsResponse
import com.example.films.data.dto.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.*

interface IMDbApiService {


    @GET("/en/API/SearchMovie/k_1ap5tjd2/{expression}")
    fun searchMovies(@Path("expression") expression:String): Call<MoviesSearchResponse>

    @GET("/en/API/Title/k_1ap5tjd2/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MovieDetailsResponse>


}