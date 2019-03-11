package com.mobgen.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MediaEntity(
    @Expose
    @SerializedName("media_url_https")
    val mediaUrlHttps: String,
    @Expose
    @SerializedName("type")
    val type: String,
    @Expose
    @SerializedName("video_info")
    val videoInfo: VideoInfoEntity?

)
