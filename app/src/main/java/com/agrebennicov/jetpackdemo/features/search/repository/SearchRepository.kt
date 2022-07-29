package com.agrebennicov.jetpackdemo.features.search.repository

import com.agrebennicov.jetpackdemo.common.database.JokeDao
import com.agrebennicov.jetpackdemo.common.di.IO
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import com.agrebennicov.jetpackdemo.common.pojo.SearchJokeResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchService: SearchService,
    private val jokeDao: JokeDao,
    @IO private val IO: CoroutineDispatcher,
) {
    suspend fun searchJokes(query: String): Flow<Result<SearchJokeResponse>> =
        flow { emit(Result.success(searchService.searchJokes(query))) }
            .flowOn(IO)
            .catch { emit(Result.failure(it)) }

    suspend fun addJoke(joke: Joke) = withContext(IO) { jokeDao.addJoke(joke) }

    suspend fun deleteJoke(joke: Joke) = withContext(IO) { jokeDao.deleteJoke(joke) }
}
