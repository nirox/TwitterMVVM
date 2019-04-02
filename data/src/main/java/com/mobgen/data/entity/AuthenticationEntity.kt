package com.mobgen.data.entity

import com.google.gson.annotations.SerializedName

data class AuthenticationEntity(
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("access_token")
    val accessToken: String
)