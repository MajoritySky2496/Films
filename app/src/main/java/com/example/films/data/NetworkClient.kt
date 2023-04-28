package com.example.films.data

import com.example.films.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response

}