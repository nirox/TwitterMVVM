package com.mobgen.presentation.tweet

import com.mobgen.domain.subscribe
import com.mobgen.domain.useCase.GetTweetById
import com.mobgen.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class TweetViewModel(
    private val getTweetById: GetTweetById,
    private val tweetBindViewMapper: TweetBindViewMapper
) : BaseViewModel<TweetViewModel.TweetViewData>() {
    private val tweetViewData = TweetViewData()

    fun loadData(id: Long) {
        data.value = tweetViewData.apply { status = Status.LOADING }
        executeUseCase {
            getTweetById.execute(id).subscribe(
                executor = AndroidSchedulers.mainThread(),
                onSuccess = {
                    data.postValue(tweetViewData.apply {
                        status = Status.SUCCESS
                        tweet = tweetBindViewMapper.map(it)
                    })
                },
                onError = {
                    data.postValue(tweetViewData.apply {
                        status = Status.ERROR
                    })
                    throw it
                }
            )
        }
    }

    class TweetViewData(
        override var status: Status = Status.LOADING,
        var tweet: TweetBindView = TweetBindView()
    ) : BaseViewModel.Data
}