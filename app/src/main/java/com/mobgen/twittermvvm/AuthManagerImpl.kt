package com.mobgen.twittermvvm

import android.content.Context
import com.mobgen.data.AuthManager

class AuthManagerImpl(context: Context) : AuthManager {

    companion object {
        private const val SHARED_PREFERENCE_NAME = "twitterMVVM-Preferences"
        private const val BEARER = "bearer"
    }

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

    override fun getAuthBearer() = sharedPreferences.getString(BEARER, "")

    override fun setAuthBearer(bearer: String) = sharedPreferences.edit().putString(BEARER, bearer).commit()
}