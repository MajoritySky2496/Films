package com.example.films

import retrofit2.Call
import retrofit2.http.*

interface ImdbApi {


    @GET("/en/API/SearchMovie/k_1ap5tjd2/{expression}")
    fun getFilms(@Path("expression") expression:String): Call<FilmsResponse>


}