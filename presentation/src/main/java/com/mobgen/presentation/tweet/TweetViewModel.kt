package com.mobgen.presentation.tweet

import com.mobgen.domain.subscribe
import com.mobgen.domain.useCase.GetTweetById
import com.mobgen.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers


class TweetViewModel(
    private val getTweetById: GetTweetById,
    private val tweetBindViewMapper: TweetBindViewMapper
) : BaseViewModel() {
    lateinit var tweet: TweetBindView

    fun loadData(id: Long) {
        status.value = Status.LOADING
        executeUseCase {
            getTweetById.execute(id).subscribe(
                executor = AndroidSchedulers.mainThread(),
                onSuccess = {
                    tweet = tweetBindViewMapper.map(it)
                    status.postValue(Status.SUCCESS)
                },
                onError = {
                    status.postValue(Status.ERROR)
                    throw it
                }
            )
        }
    }
}