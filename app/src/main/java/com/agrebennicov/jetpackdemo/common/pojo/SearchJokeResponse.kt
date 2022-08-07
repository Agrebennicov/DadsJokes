package com.agrebennicov.jetpackdemo.common.pojo

import com.google.gson.annotations.SerializedName

data class SearchJokeResponse(
    @SerializedName("results")
    val jokes: List<JokeResponse>
)
