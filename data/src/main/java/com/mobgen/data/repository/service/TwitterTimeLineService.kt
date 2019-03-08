package com.mobgen.data.repository.service

import TwitterApiConstants
import com.mobgen.data.entity.StatusesEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface TwitterTimeLineService {
    @GET(TwitterApiConstants.API_TWITTER_TIME_LINE)
    fun getTimeLine(
        @Query("user_id") userId: String? = null,
        @Query("screen_name") screenName: String? = null,
        @Query("since_id") sinceId: String? = null,
        @Query("count") count: String? = null,
        @Query("max_id") maxId: String? = null,
        @Query("trim_user") trimUser: Boolean = false,
        @Query("exclude_replies") excludeReplies: Boolean = true,
        @Query("include_rts") IncludeRTS: Boolean = false
    ): Single<List<StatusesEntity>>
}