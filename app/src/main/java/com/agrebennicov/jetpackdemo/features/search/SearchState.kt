package com.agrebennicov.jetpackdemo.features.search

import com.agrebennicov.jetpackdemo.common.pojo.Joke

data class SearchState(
    val query: String = "",
    val jokes: List<Joke> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val showData: Boolean = false
)

sealed class SearchAction {
    data class Search(val query: String) : SearchAction()
    data class QueryChanged(val query: String) : SearchAction()
    data class JokesLoaded(
        val jokes: List<Joke>,
        val currentPage: Int,
        val nextPage: Int,
        val totalPages: Int
    ) : SearchAction()

    data class JokeSaved(val joke: Joke) : SearchAction()

    data class JokeUnSaved(val joke: Joke) : SearchAction()

    object Error : SearchAction()
}
