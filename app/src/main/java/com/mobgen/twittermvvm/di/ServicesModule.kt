package com.mobgen.twittermvvm.di

import TwitterApiConstants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mobgen.data.repository.AuthenticationInterceptor
import com.mobgen.data.repository.service.AuthenticationService
import com.mobgen.data.repository.service.TwitterSearchService
import com.mobgen.data.repository.service.TwitterTimeLineService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ServicesModule {
    @Provides
    internal fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    internal fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authenticationInterceptor: AuthenticationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authenticationInterceptor)
            .build()
    }

    @Provides
    internal fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    internal fun provideAuthenticationRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TwitterApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideAuthenticationService(retrofit: Retrofit): AuthenticationService {
        return retrofit.create(AuthenticationService::class.java)
    }

    @Provides
    fun provideTwitterSearchService(retrofit: Retrofit): TwitterSearchService {
        return retrofit.create(TwitterSearchService::class.java)
    }

    @Provides
    fun provideTwitterTimeLineService(retrofit: Retrofit): TwitterTimeLineService {
        return retrofit.create(TwitterTimeLineService::class.java)
    }
}