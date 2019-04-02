package com.mobgen.data.entity

import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("name")
    val name: String,
    @SerializedName("screen_name")
    val screenName: String,
    @SerializedName("profile_image_url_https")
    val profileImage: String

)
