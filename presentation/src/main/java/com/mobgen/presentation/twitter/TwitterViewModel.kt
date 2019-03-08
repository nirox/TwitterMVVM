package com.mobgen.presentation.twitter

import com.mobgen.domain.subscribe
import com.mobgen.domain.useCase.GetTimeLine
import com.mobgen.domain.useCase.Search
import com.mobgen.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class TwitterViewModel @Inject constructor(
    private val getTimeLine: GetTimeLine,
    private val search: Search,
    private val tweetBindViewMapper: TweetBindViewMapper
) : BaseViewModel() {
    var tweets = listOf<TweetBindView>()
    private var searchQuery = ""

    fun loadData() {
        status.value = Status.LOADING
        executeUseCase {
            getTimeLine.execute().subscribe(
                executor = AndroidSchedulers.mainThread(),
                onSuccess = { tweets ->
                    this.tweets = tweets.map(tweetBindViewMapper::map)
                    status.postValue(Status.SUCCESS)

                },
                onError = {
                    status.postValue(Status.ERROR)
                    throw  it
                }
            )
        }

    }

    fun loadData(searchQuery: String) {
        this.searchQuery = searchQuery
        status.value = Status.LOADING
        executeUseCase {
            search.execute(searchQuery).subscribe(
                executor = AndroidSchedulers.mainThread(),
                onSuccess = { tweets ->
                    this.tweets = tweets.map(tweetBindViewMapper::map)
                    status.postValue(Status.SUCCESS)

                },
                onError = {
                    status.postValue(Status.ERROR)
                    throw  it
                }
            )
        }
    }

    fun nextPage() = if (searchQuery.isNotBlank()) nextPageSearch() else nextPageTimeLine()

    private fun nextPageSearch() {
        status.value = Status.LOADING_NEXT_PAGE
        executeUseCase {
            search.next(searchQuery).subscribe(
                executor = AndroidSchedulers.mainThread(),
                onSuccess = { tweets ->
                    val aux = this.tweets.toMutableList()
                    aux.addAll(tweets.map(tweetBindViewMapper::map))
                    this.tweets = aux
                    status.postValue(Status.SUCCESS)

                },
                onError = {
                    status.postValue(Status.ERROR)
                    throw  it
                }
            )
        }
    }

    private fun nextPageTimeLine() {
        status.value = Status.LOADING_NEXT_PAGE
        executeUseCase {
            getTimeLine.next().subscribe(
                executor = AndroidSchedulers.mainThread(),
                onSuccess = { tweets ->
                    val aux = this.tweets.toMutableList()
                    aux.addAll(tweets.map(tweetBindViewMapper::map))
                    this.tweets = aux
                    status.postValue(Status.SUCCESS)
                },
                onError = {
                    status.postValue(Status.ERROR)
                    throw  it
                }
            )
        }
    }
}