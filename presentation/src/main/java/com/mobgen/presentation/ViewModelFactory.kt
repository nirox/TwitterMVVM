package com.mobgen.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.mobgen.domain.useCase.Authenticate
import com.mobgen.domain.useCase.GetTimeLine
import com.mobgen.domain.useCase.GetTweetById
import com.mobgen.domain.useCase.Search
import com.mobgen.presentation.tweet.TweetBindViewMapper
import com.mobgen.presentation.tweet.TweetViewModel
import com.mobgen.presentation.twitter.TwitterListBindViewMapper
import com.mobgen.presentation.twitter.TwitterViewModel
import javax.inject.Inject


class ViewModelFactory @Inject constructor(
    private val authenticate: Authenticate,
    private val getTimeLine: GetTimeLine,
    private val search: Search,
    private val twitterListBindViewMapper: TwitterListBindViewMapper,
    private val getTweetById: GetTweetById,
    private val tweetbindViewMapper: TweetBindViewMapper
) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(TwitterViewModel::class.java) -> TwitterViewModel(
                getTimeLine,
                search,
                twitterListBindViewMapper
            )
            modelClass.isAssignableFrom(TweetViewModel::class.java) -> TweetViewModel(
                getTweetById,
                tweetbindViewMapper
            )
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                authenticate
            )
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T
    }

}