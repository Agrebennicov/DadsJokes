package com.agrebennicov.jetpackdemo.common.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.agrebennicov.jetpackdemo.common.pojo.Joke

@Dao
interface JokeDao {
    @Query("SELECT * FROM $JOKES_TABLE ORDER BY id ASC")
    fun getAllJokes(): List<Joke>

    @Insert(onConflict = IGNORE)
    fun addJoke(joke: Joke)

    @Delete
    fun deleteJoke(joke: Joke)

    @Delete
    fun deleteJokes(jokes: List<Joke>)

    @Query("SELECT id FROM $JOKES_TABLE WHERE id IN (:ids)")
    fun getStoredJokesIdsByIds(ids: List<String>): List<String>

    @Query("SELECT id FROM $JOKES_TABLE WHERE id IS :id")
    fun getStoredJokeIdById(id: String): String?

    @Query("SELECT * FROM $JOKES_TABLE WHERE id IS :id")
    fun getJokeById(id: String): Joke?

    @Query("SELECT * FROM $JOKES_TABLE WHERE content LIKE :query")
    fun searchJokes(query: String): List<Joke>
}
