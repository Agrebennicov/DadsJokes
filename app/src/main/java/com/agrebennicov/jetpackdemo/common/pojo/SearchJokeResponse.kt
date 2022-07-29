package com.agrebennicov.jetpackdemo.common.pojo

import com.google.gson.annotations.SerializedName

data class SearchJokeResponse(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("next_page")
    val nextPage: Int,
    @SerializedName("results")
    val jokes: List<JokeResponse>,
    @SerializedName("total_pages")
    val totalPages: Int
)
