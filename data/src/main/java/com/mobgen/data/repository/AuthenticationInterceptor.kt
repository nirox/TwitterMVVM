package com.mobgen.data.repository

import TwitterApiConstants
import com.mobgen.data.AuthManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(private val sharedPreferencesManager: AuthManager) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader(
                TwitterApiConstants.AUTHORIZATION_HEADER_KEY,
                String.format(TwitterApiConstants.AUTHORIZATION_HEADER_VALUE, sharedPreferencesManager.getAuthBearer())
            )
            .addHeader(
                TwitterApiConstants.AUTHORIZATION_HEADER_CONTENT_TYPE_KEY,
                TwitterApiConstants.AUTHORIZATION_HEADER_CONTENT_TYPE_VALUE
            )
            .build()

        return chain.proceed(request)
    }
}