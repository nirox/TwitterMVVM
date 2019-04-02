package com.mobgen.twittermvvm.di

import android.content.Context
import com.mobgen.presentation.MainActivity
import com.mobgen.presentation.tweet.TweetFragment
import com.mobgen.presentation.twitter.TwitterListFragment
import com.mobgen.twittermvvm.TwitterApplication
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {
    @ContributesAndroidInjector
    abstract fun get(): MainActivity

    @ContributesAndroidInjector
    abstract fun twitterListFragment(): TwitterListFragment

    @ContributesAndroidInjector
    abstract fun tweetFragment(): TweetFragment

    @Binds
    abstract fun provideApplicationContext(application: TwitterApplication): Context
}