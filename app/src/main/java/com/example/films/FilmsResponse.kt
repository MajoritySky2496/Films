package com.example.films

class FilmsResponse(
    val searchType: String,
    val expression: String,
    val results: List<Films>
) {
}