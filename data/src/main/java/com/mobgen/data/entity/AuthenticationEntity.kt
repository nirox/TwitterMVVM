package com.mobgen.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthenticationEntity(
    @Expose
    @SerializedName("token_type")
    val tokenType: String,
    @Expose
    @SerializedName("access_token")
    val accessToken: String
)