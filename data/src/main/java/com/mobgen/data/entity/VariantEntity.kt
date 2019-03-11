package com.mobgen.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VariantEntity(
    @Expose
    @SerializedName("url")
    val url: String
)
