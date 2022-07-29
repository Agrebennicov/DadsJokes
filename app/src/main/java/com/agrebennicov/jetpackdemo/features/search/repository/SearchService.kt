package com.agrebennicov.jetpackdemo.features.search.repository

import com.agrebennicov.jetpackdemo.common.pojo.SearchJokeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search")
    suspend fun searchJokes(
        @Query("term") query: String,
        @Query("limit") limit: Int = 30
    ): SearchJokeResponse
}
