package com.mobgen.domain

import com.mobgen.domain.model.Tweet
import io.reactivex.Completable
import io.reactivex.Single

interface TwitterRepository {
    fun authenticateApp(): Completable
    fun getTimeLine(): Single<List<Tweet>>
    fun getNextTimeLine(maxId: String): Single<List<Tweet>>
    fun getTweetById(id: Long): Single<Tweet>
    fun search(search: String): Single<List<Tweet>>
    fun getNextSearch(search: String, maxId: String): Single<List<Tweet>>
}