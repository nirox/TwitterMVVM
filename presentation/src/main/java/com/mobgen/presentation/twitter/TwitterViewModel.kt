package com.mobgen.presentation.twitter

import com.mobgen.domain.subscribe
import com.mobgen.domain.useCase.GetTimeLine
import com.mobgen.domain.useCase.Search
import com.mobgen.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class TwitterViewModel(
    private val getTimeLine: GetTimeLine,
    private val search: Search,
    private val tweetBindViewMapper: TwitterListBindViewMapper
) : BaseViewModel<TwitterViewModel.TwitterViewData>() {
    private var twitterViewData = TwitterViewModel.TwitterViewData(Status.LOADING, listOf(), "")

    init {
        data.value = twitterViewData
    }

    fun loadData(force: Boolean = false) {
        if (twitterViewData.tweets.isEmpty() || force) {
            data.value = twitterViewData.apply { status = Status.LOADING }
            executeUseCase {
                getTimeLine.execute().subscribe(
                    executor = AndroidSchedulers.mainThread(),
                    onSuccess = { tweetsReponse ->
                        data.postValue(twitterViewData.apply {
                            tweets = tweetsReponse.map(tweetBindViewMapper::map)
                            status = Status.SUCCESS
                        })

                    },
                    onError = {
                        data.postValue(twitterViewData.apply {
                            status = Status.ERROR
                        })
                        throw  it
                    }
                )
            }
        }
    }

    fun loadData(query: String, force: Boolean = false) {
        if (twitterViewData.tweets.isEmpty() || force) {
            data.value = twitterViewData.apply {
                searchQuery = query
                status = Status.LOADING
            }
            executeUseCase {
                search.execute(query).subscribe(
                    executor = AndroidSchedulers.mainThread(),
                    onSuccess = { tweetsReponse ->
                        data.postValue(twitterViewData.apply {
                            tweets = tweetsReponse.map(tweetBindViewMapper::map)
                            status = Status.SUCCESS
                        })

                    },
                    onError = {
                        data.postValue(twitterViewData.apply {
                            status = Status.ERROR
                        })
                        throw  it
                    }
                )
            }
        }
    }

    fun nextPage() = if (twitterViewData.searchQuery.isNotBlank()) nextPageSearch() else nextPageTimeLine()

    private fun nextPageSearch() {
        data.value = twitterViewData.apply { status = Status.LOADING_NEXT_PAGE }
        executeUseCase {
            search.next(twitterViewData.searchQuery).subscribe(
                executor = AndroidSchedulers.mainThread(),
                onSuccess = { tweetsResponse ->
                    val aux = twitterViewData.tweets.toMutableList()
                    aux.addAll(tweetsResponse.map(tweetBindViewMapper::map))
                    data.postValue(twitterViewData.apply {
                        status = Status.SUCCESS
                        tweets = aux
                    })

                },
                onError = {
                    data.postValue(twitterViewData.apply {
                        status = Status.ERROR
                    })
                    throw  it
                }
            )
        }
    }

    private fun nextPageTimeLine() {
        data.value = twitterViewData.apply { status = Status.LOADING_NEXT_PAGE }
        executeUseCase {
            getTimeLine.next().subscribe(
                executor = AndroidSchedulers.mainThread(),
                onSuccess = { tweetsResponse ->
                    val aux = twitterViewData.tweets.toMutableList()
                    aux.addAll(tweetsResponse.map(tweetBindViewMapper::map))
                    data.postValue(twitterViewData.apply {
                        status = Status.SUCCESS
                        tweets = aux
                    })
                },
                onError = {
                    data.postValue(twitterViewData.apply {
                        status = Status.ERROR
                    })
                    throw  it
                }
            )
        }
    }

    class TwitterViewData(override var status: Status, var tweets: List<TweetBindView>, var searchQuery: String) : Data
}