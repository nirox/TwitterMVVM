package com.mobgen.presentation.tweet

data class TweetBindView (
    val image: String,
    val id: String,
    val name: String,
    val content:String,
    val medias : List<String>,
    val videos: List<String>
)
