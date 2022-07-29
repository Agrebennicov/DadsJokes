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
    fun getJokes(): List<Joke>

    @Insert(onConflict = IGNORE)
    fun addJoke(joke: Joke)

    @Delete
    fun deleteJoke(joke: Joke)
}
