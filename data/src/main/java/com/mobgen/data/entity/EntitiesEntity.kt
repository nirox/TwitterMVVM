package com.mobgen.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EntitiesEntity (
    @Expose
    @SerializedName("media")
    val mediaEntityList: List<MediaEntity>
)
