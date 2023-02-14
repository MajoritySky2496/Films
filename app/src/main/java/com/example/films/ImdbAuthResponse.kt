package com.example.films

import com.google.gson.annotations.SerializedName

class ImdbAuthResponse(@SerializedName("access_token") val token: String) {
}