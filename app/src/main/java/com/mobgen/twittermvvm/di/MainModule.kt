package com.mobgen.twittermvvm.di

import com.mobgen.presentation.MainActivity
import com.mobgen.presentation.twitter.TwitterListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [
    ]
)
abstract class MainModule {
    @ContributesAndroidInjector
    abstract fun get(): MainActivity

    @ContributesAndroidInjector
    abstract fun twitterListFragment(): TwitterListFragment
}