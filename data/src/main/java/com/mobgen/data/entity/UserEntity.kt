package com.mobgen.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserEntity(
    @Expose
    val name: String,
    @Expose
    @SerializedName("screen_name")
    val screenName: String,
    @Expose
    @SerializedName("profile_image_url_https")
    val profileImage: String

)
