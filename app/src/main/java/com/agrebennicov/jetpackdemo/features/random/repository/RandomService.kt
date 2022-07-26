package com.agrebennicov.jetpackdemo.features.random.repository

import com.agrebennicov.jetpackdemo.common.pojo.JokeResponse
import retrofit2.http.GET

interface RandomService {
    @GET(".")
    suspend fun fetchRandomJoke(): JokeResponse
}