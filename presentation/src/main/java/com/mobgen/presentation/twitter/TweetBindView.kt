package com.mobgen.presentation.twitter

data class TweetBindView (
    val id: Long,
    val image: String,
    val userId: String,
    val name: String,
    val content: String
)