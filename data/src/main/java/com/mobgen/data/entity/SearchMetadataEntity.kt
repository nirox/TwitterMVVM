package com.mobgen.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchMetadataEntity(
    @Expose
    @SerializedName("max_id")
    val maxId: String,
    @Expose
    @SerializedName("next_results")
    val nextResults: String,
    @Expose
    @SerializedName("count")
    val count: Int
)
