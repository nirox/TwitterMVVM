package com.mobgen.data.entity

import com.google.gson.annotations.SerializedName

data class StatusesEntity(
    @SerializedName("text")
    val text: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("entities")
    val entity: EntitiesEntity?,
    @SerializedName("extended_entities")
    val extendedEntities: EntitiesEntity?,
    @SerializedName("user")
    val userEntity: UserEntity
)
