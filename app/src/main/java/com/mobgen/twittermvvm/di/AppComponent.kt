package com.mobgen.twittermvvm.di

import com.mobgen.twittermvvm.TwitterApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        MainModule::class,
        ApplicationModule::class,
        ServicesModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent : AndroidInjector<TwitterApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TwitterApplication>()
}