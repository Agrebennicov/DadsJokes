package com.agrebennicov.jetpackdemo.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.agrebennicov.jetpackdemo.common.pojo.Joke

const val JOKES_DATABASE = "jokes_db"
const val JOKES_TABLE = "jokes"

@Database(entities = [Joke::class], version = 2, exportSchema = false)
abstract class JokesDb: RoomDatabase() {
    abstract fun jokeDao(): JokeDao
}
