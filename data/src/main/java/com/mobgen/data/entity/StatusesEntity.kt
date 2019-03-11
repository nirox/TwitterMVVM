package com.mobgen.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StatusesEntity(
    @Expose
    val text: String,
    @Expose
    val id: Long,
    @Expose
    @SerializedName("entities")
    val entity: EntitiesEntity?,
    @Expose
    @SerializedName("extended_entities")
    val extendedEntities: EntitiesEntity?,
    @Expose
    @SerializedName("user")
    val userEntity: UserEntity
)
