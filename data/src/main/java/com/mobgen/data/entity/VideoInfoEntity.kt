package com.mobgen.data.entity

import com.google.gson.annotations.SerializedName

data class VideoInfoEntity(
    @SerializedName("variants")
    val variants: List<VariantEntity>? = listOf()
)