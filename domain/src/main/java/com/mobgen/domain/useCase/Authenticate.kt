package com.mobgen.domain.useCase

import com.mobgen.domain.TwitterRepository
import io.reactivex.Completable
import javax.inject.Inject

class Authenticate @Inject constructor(private val twitterRepository: TwitterRepository) {
    fun execute(): Completable {
        return twitterRepository.authenticateApp()
    }
}