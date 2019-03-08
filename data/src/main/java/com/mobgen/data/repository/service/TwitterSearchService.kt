package com.mobgen.data.repository.service

import TwitterApiConstants
import com.mobgen.data.entity.SearchTweetsEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TwitterSearchService {
    @GET(TwitterApiConstants.API_TWITTER_SEARCH)
    fun getTweetList(
        @Query("q") queries: String,
        @Query("result_type") resultType: String? = null,
        @Query("max_id") maxId: String? = null,
        @Query("count") count: String? = null,
        @Query("tweet_mode") mode: String? = null,
        @Query("include_entities") includeEntities: Boolean = false
    ): Single<SearchTweetsEntity>
}