package com.mobgen.data.repository.service

import com.mobgen.data.entity.AuthenticationEntity
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationService {
    @FormUrlEncoded
    @POST(TwitterApiConstants.API_TWITTER_AUTHENTICATION)
    fun getAuthentication(
        @Header("Authorization") authorization: String,
        @Field("grant_type") grantType: String
    ): Single<AuthenticationEntity>

}