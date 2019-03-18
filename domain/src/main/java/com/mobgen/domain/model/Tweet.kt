package com.mobgen.domain.model

import java.util.*

data class Tweet(
    val id: Long,
    val image: String,
    val userId: String,
    val name: String,
    val content: String,
    val medias: List<String>,
    val videos: List<String>,
    val date: Date
) {
    override fun equals(other: Any?): Boolean {
        return (other as Tweet).id == this.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}