package com.mobgen.domain.useCase

import com.mobgen.domain.TwitterRepository
import com.mobgen.domain.model.Tweet
import io.reactivex.Single
import javax.inject.Inject

class GetTimeLine @Inject constructor(private val repository: TwitterRepository) {
    companion object {
        private var maxId: Long = -1
    }

    fun execute(): Single<List<Tweet>> {
        return repository.getTimeLine().map { it.also { tweets -> updateMaxId(tweets) } }
    }

    fun next(): Single<List<Tweet>> {
        return if (maxId != -1L) {
            repository.getNextTimeLine(maxId.toString()).map { it.also { tweets -> updateMaxId(tweets) } }
        } else {
            repository.getTimeLine()
        }
    }

    private fun updateMaxId(tweets: List<Tweet>) {
        if (tweets.isNotEmpty()) maxId = tweets.last().id - 1
    }

}