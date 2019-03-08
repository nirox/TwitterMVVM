package com.mobgen.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchTweetsEntity(
    @Expose
    @SerializedName("statuses")
    val statusesEntityList: List<StatusesEntity>,
    @Expose
    @SerializedName("search_metadata")
    val searchMetadataEntity: SearchMetadataEntity
)
