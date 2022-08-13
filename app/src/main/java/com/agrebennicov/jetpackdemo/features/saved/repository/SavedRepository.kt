package com.agrebennicov.jetpackdemo.features.saved.repository

import com.agrebennicov.jetpackdemo.common.database.JokeDao
import com.agrebennicov.jetpackdemo.common.di.IO
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SavedRepository @Inject constructor(
    private val jokeDao: JokeDao,
    @IO private val IO: CoroutineDispatcher,
) {
    suspend fun getAllJokes() = withContext(IO) { jokeDao.getAllJokes() }

    suspend fun deleteJoke(joke: Joke) = withContext(IO) { jokeDao.deleteJoke(joke) }

    suspend fun deleteJokes(jokes: List<Joke>) = withContext(IO) { jokeDao.deleteJokes(jokes) }
}