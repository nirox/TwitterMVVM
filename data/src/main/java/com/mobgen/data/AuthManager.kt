package com.mobgen.data

interface AuthManager {
    fun getAuthBearer(): String
    fun setAuthBearer(bearer: String): Boolean
}