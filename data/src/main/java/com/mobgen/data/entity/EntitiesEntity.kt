package com.mobgen.data.entity

import com.google.gson.annotations.SerializedName

data class EntitiesEntity(
    @SerializedName("media")
    val mediaEntityList: List<MediaEntity> = listOf()
)
