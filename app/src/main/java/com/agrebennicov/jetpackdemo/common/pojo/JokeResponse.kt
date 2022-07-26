package com.agrebennicov.jetpackdemo.common.pojo

import com.google.gson.annotations.SerializedName

data class JokeResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("joke")
    val content: String
)