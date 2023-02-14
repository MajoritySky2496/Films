package com.example.films

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val imdbBaseUrl = "https://imdb-api.com"

    private var retrofit = Retrofit.Builder().baseUrl(imdbBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val filmsService = retrofit.create(ImdbApi::class.java)
    private val films = ArrayList<Films>()
    private val adapter = FilmsAdapter()

    private lateinit var searchButton: Button
    private lateinit var expressionInput: EditText
    private lateinit var filmsList: RecyclerView
    private lateinit var placeHolderMessage: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchButton = findViewById(R.id.search_button)
        expressionInput = findViewById(R.id.expressionInput)
        filmsList = findViewById(R.id.content)
        placeHolderMessage = findViewById(R.id.placeholderMessage)


        filmsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.films = films

        searchButton.setOnClickListener{
                if (expressionInput.text.isNotEmpty()){
                    filmsService.getFilms(expressionInput.text.toString()).enqueue(object : Callback<FilmsResponse>{
                        @SuppressLint("NotifyDataSetChanged")
                        override fun onResponse(
                            call: Call<FilmsResponse>,
                            response: Response<FilmsResponse>
                        ) {
                                if (response.code() == 200){
                                    films.clear()
                                    if (response.body()?.results?.isNotEmpty() == true){
                                        films.addAll(response.body()?.results!!)
                                        adapter.notifyDataSetChanged()
                                    }
                                    if(films.isEmpty()){
                                        showMessage(getString(R.string.nothing_found), "")
                                    }else{
                                        showMessage("","")
                                    }
                                }else{
                                    showMessage(getString(R.string.something_went_wrong), response.code().toString())
                                }
                            }

                        override fun onFailure(call: Call<FilmsResponse>, t: Throwable) {
                            showMessage(getString(R.string.something_went_wrong), t.message.toString())
                        }


                    })
                }
        }



    }
    @SuppressLint("NotifyDataSetChanged")
    private fun showMessage(text: String, additionalMessage:String){
        if(text.isNotEmpty()){
            placeHolderMessage.visibility = View.VISIBLE
            films.clear()
            adapter.notifyDataSetChanged()
            placeHolderMessage.text = text
            if(additionalMessage.isNotEmpty()){
                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG).show()
            }
        }else{
            placeHolderMessage.visibility = View.GONE
        }

    }
//    private fun authenticate(){
//        filmsService.authenticate(ImdbAuthRequest("gerzag96", "German2496!"))
//            .enqueue(object: Callback<ImdbAuthResponse>{
//                override fun onResponse(
//                    call: Call<ImdbAuthResponse>,
//                    response: Response<ImdbAuthResponse>
//                ) {
//                    if (response.code() == 200){
//                        token = response.body()?.token.toString()
//                        search()
//                    }else{
//                        showMessage(getString(R.string.something_went_wrong), response.code().toString())
//                    }
//                }
//
//                override fun onFailure(call: Call<ImdbAuthResponse>, t: Throwable) {
//                    showMessage(getString(R.string.something_went_wrong), t.message.toString())
//                }
//            })
//    }
//    private fun search(){
//        filmsService.getFilms(expressionInput.text.toString())
//            .enqueue(object : Callback<FilmsResponse>{
//                override fun onResponse(
//                    call: Call<FilmsResponse>,
//                    response: Response<FilmsResponse>
//                ) {
//                    when(response.code()){
//                        200 ->{
//                            if (response.body()?.results?.isNotEmpty() == true){
//                                films.clear()
//                                films.addAll(response.body()?.results!!)
//                                adapter.notifyDataSetChanged()
//
//                            }else{
//                                showMessage(getString(R.string.nothing_found), "")
//                            }
//                        }
//                        else -> showMessage(getString(R.string.something_went_wrong), response.code().toString())
//                    }
//                }
//
//                override fun onFailure(call: Call<FilmsResponse>, t: Throwable) {
//                    showMessage (getString(R.string.something_went_wrong), t.message.toString())
//                }
//            })
//    }


}