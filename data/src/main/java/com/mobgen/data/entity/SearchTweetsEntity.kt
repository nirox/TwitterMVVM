package com.mobgen.data.entity

import com.google.gson.annotations.SerializedName

data class SearchTweetsEntity(
    @SerializedName("statuses")
    val statusesEntityList: List<StatusesEntity>,
    @SerializedName("search_metadata")
    val searchMetadataEntity: SearchMetadataEntity
)
