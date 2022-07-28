package com.agrebennicov.jetpackdemo.features.random.repository

import android.util.Log
import com.agrebennicov.jetpackdemo.common.database.JokeDao
import com.agrebennicov.jetpackdemo.common.di.IO
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import com.agrebennicov.jetpackdemo.common.pojo.JokeResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RandomRepository @Inject constructor(
    private val randomService: RandomService,
    private val jokeDao: JokeDao,
    @IO private val IO: CoroutineDispatcher,
) {

    suspend fun fetchRandomJoke(): Flow<Result<JokeResponse>> =
        flow { emit(Result.success(randomService.fetchRandomJoke())) }
            .flowOn(IO)
            .catch { emit(Result.failure(it)) }

    suspend fun addJoke(joke: Joke): Flow<Result<Unit>> =
        flow { emit(Result.success(jokeDao.addJoke(joke))) }
            .flowOn(IO)
            .catch { emit(Result.failure(it)) }

    suspend fun deleteJoke(joke: Joke): Flow<Result<Unit>> =
        flow { emit(Result.success(jokeDao.deleteJoke(joke))) }
            .flowOn(IO)
            .catch { emit(Result.failure(it)) }

    fun getSavedJokes(): Flow<Result<List<Joke>>> =
        flow { emit(Result.success(jokeDao.getJokes())) }
            .flowOn(IO)
            .catch { emit(Result.failure(it)) }
}
