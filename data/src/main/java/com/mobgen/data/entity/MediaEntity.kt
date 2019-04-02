package com.mobgen.data.entity

import com.google.gson.annotations.SerializedName

data class MediaEntity(
    @SerializedName("media_url_https")
    val mediaUrlHttps: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("video_info")
    val videoInfo: VideoInfoEntity?

)
