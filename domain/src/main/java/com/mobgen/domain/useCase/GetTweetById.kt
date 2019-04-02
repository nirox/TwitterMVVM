package com.mobgen.domain.useCase

import com.mobgen.domain.TwitterRepository
import com.mobgen.domain.model.Tweet
import io.reactivex.Single
import javax.inject.Inject

class GetTweetById @Inject constructor(private val repository: TwitterRepository) {
    fun execute(id: Long): Single<Tweet> = repository.getTweetById(id)
}