package com.agrebennicov.jetpackdemo.features.random.repository

import com.agrebennicov.jetpackdemo.common.database.JokeDao
import com.agrebennicov.jetpackdemo.common.di.IO
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RandomRepository @Inject constructor(
    private val randomService: RandomService,
    private val jokeDao: JokeDao,
    @IO private val IO: CoroutineDispatcher,
) {
    suspend fun fetchRandomJoke(): Flow<Result<Joke>> =
        flow {
            val response = randomService.fetchRandomJoke()
            val localJokeId = jokeDao.getStoredJokeIdById(response.id)
            val result = Joke(response, localJokeId != null)
            emit(Result.success(result))
        }
            .flowOn(IO)
            .catch { emit(Result.failure(it)) }

    suspend fun getJoke(id: String) = withContext(IO) { jokeDao.getJokeById(id) }

    suspend fun addJoke(joke: Joke) = withContext(IO) { jokeDao.addJoke(joke) }

    suspend fun deleteJoke(joke: Joke) = withContext(IO) { jokeDao.deleteJoke(joke) }
}