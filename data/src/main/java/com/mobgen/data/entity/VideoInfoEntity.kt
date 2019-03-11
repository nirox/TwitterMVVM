package com.mobgen.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VideoInfoEntity (
    @Expose
    @SerializedName("variants")
    val variants: List<VariantEntity>? = listOf()
)