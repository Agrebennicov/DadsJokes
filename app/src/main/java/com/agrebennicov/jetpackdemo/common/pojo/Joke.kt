package com.agrebennicov.jetpackdemo.common.pojo

data class Joke(
    val content: String,
    val isSaved: Boolean,
    val isSelected: Boolean
) {
    constructor(jokeResponse: JokeResponse) : this(
        jokeResponse.content,
        false,
        false
    )
}
