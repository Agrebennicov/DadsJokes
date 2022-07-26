package com.agrebennicov.jetpackdemo.features.random.repository

import com.agrebennicov.jetpackdemo.common.di.IO
import com.agrebennicov.jetpackdemo.common.pojo.JokeResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RandomRepository @Inject constructor(
    private val randomService: RandomService,
    @IO private val IO: CoroutineDispatcher,
) {

    suspend fun fetchRandomJoke(): Flow<Result<JokeResponse>> =
        flow { emit(Result.success(randomService.fetchRandomJoke())) }
            .flowOn(IO)
            .catch { emit(Result.failure(it)) }
}
