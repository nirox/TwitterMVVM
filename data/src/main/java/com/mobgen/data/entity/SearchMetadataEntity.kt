package com.mobgen.data.entity

import com.google.gson.annotations.SerializedName

data class SearchMetadataEntity(
    @SerializedName("max_id")
    val maxId: String,
    @SerializedName("next_results")
    val nextResults: String,
    @SerializedName("count")
    val count: Int
)
