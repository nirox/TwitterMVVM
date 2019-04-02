package com.mobgen.twittermvvm.di

import android.content.Context
import com.mobgen.data.AuthManager
import com.mobgen.twittermvvm.EncodedCredentials
import com.mobgen.twittermvvm.AuthManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ApplicationModule {
    @Provides
    fun provideAuthManager(context: Context): AuthManager =
        AuthManagerImpl(context)

    @Provides
    @Named("token")
    internal fun provideTwitterToken(): String {
        return EncodedCredentials.getEncodeCredential()
    }

}
