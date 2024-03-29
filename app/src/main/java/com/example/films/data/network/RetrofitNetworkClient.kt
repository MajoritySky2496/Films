package com.example.films.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.films.data.NetworkClient
import com.example.films.data.dto.MoviesSearchRequest
import com.example.films.data.dto.MovieDetailsRequest
import com.example.films.data.dto.MoviesSearchResponse
import com.example.films.data.dto.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient(private val context: Context):NetworkClient {
    private val imdbBaseUrl = "https://imdb-api.com"
    lateinit var response: Response



    private val retrofit = Retrofit.Builder()
        .baseUrl(imdbBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbService = retrofit.create(IMDbApiService::class.java)


    @RequiresApi(Build.VERSION_CODES.M)
    override fun doRequest(dto: Any): Response {
        if(isConnected() == false){
            return Response().apply { resultCode = -1 }
        }

        val response = if(dto is MoviesSearchRequest){ imdbService.searchMovies(dto.expression).execute()}
        else{ (imdbService.getMovieDetails((dto as MovieDetailsRequest).movieId)).execute()
        }
        val body = response.body()
        return if (body != null){
            body.apply { resultCode = response.code() }
        }else{
            Response().apply { resultCode = response.code() }
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isConnected():Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities!=null){
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }

}