package com.mobgen.twittermvvm.di

import android.content.Context
import com.mobgen.data.SharedPreferencesManager
import com.mobgen.twittermvvm.EncodedCredentials
import com.mobgen.twittermvvm.SharedPreferencesManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ApplicationModule {
    @Provides
    fun provideSharePreferenceManager(context: Context): SharedPreferencesManager =
        SharedPreferencesManagerImpl(context)

    @Provides
    @Named("token")
    internal fun provideTwitterToken(): String {
        return EncodedCredentials.getEncodeCredential()
    }

}
