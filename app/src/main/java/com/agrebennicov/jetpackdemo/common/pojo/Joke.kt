package com.agrebennicov.jetpackdemo.common.pojo

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.agrebennicov.jetpackdemo.common.database.JOKES_TABLE

@Entity(tableName = JOKES_TABLE)
data class Joke(
    @PrimaryKey
    val id: String,
    val content: String,
    val isSaved: Boolean,
    @Ignore
    val isSelected: Boolean
) {
    constructor(id: String, content: String, isSaved: Boolean) : this(
        id,
        content,
        isSaved,
        false
    )

    constructor(jokeResponse: JokeResponse, isSaved: Boolean) : this(
        jokeResponse.id,
        jokeResponse.content,
        isSaved,
        false
    )
}
