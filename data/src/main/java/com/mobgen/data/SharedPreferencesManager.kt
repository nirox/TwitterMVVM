package com.mobgen.data

interface SharedPreferencesManager {
    fun getAuthBearer(): String
    fun setAuthBearer(bearer: String): Boolean
}