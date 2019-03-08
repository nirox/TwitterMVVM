package com.mobgen.domain.useCase

import com.mobgen.domain.TwitterRepository
import com.mobgen.domain.model.Tweet
import io.reactivex.Single
import javax.inject.Inject

class Search @Inject constructor(private val repository: TwitterRepository) {
    companion object {
        private var maxId: Long = -1
    }

    fun execute(search: String): Single<List<Tweet>> =
        repository.search(search).map { it.also { tweets -> updateMaxId(tweets) } }

    fun next(search: String): Single<List<Tweet>> =
        if (maxId != -1L) {
            repository.getNextSearch(search, maxId.toString()).map { it.also { tweets -> updateMaxId(tweets) } }
        } else {
            repository.search(search)
        }

    private fun updateMaxId(tweets: List<Tweet>) {
        if (tweets.isNotEmpty()) maxId = tweets.last().id - 1
    }
}