package com.mobgen.twittermvvm.di

import com.mobgen.data.repository.TwitterRepositoryImpl
import com.mobgen.domain.TwitterRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideTwitteRepository(twitterRepositoryImpl: TwitterRepositoryImpl): TwitterRepository


}