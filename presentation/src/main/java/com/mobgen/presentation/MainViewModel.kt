package com.mobgen.presentation

import com.mobgen.domain.subscribe
import com.mobgen.domain.useCase.Authenticate
import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModel(private val authenticate: Authenticate) : BaseViewModel<MainViewModel.MainViewData>() {
    private var mainViewData = MainViewData(Status.LOADING)

    init {
        data.value = mainViewData
    }

    fun authenticate() {
        if (!mainViewData.auth) {
            data.value = mainViewData.apply {
                status = Status.LOADING
            }
            executeUseCase {
                authenticate.execute().subscribe(
                    executor = AndroidSchedulers.mainThread(),
                    onComplete = {
                        data.postValue(mainViewData.apply {
                            goTwitterFragment = true
                            status = Status.SUCCESS
                            auth = true
                        })
                    },
                    onError = {
                        data.postValue(mainViewData.apply { status = Status.ERROR })
                        throw  it
                    }
                )
            }
        }
    }

    fun setSearchVisibility(check: Boolean) {
        data.postValue(mainViewData.apply { searchViewVisibility = check })
    }

    class MainViewData(
        override var status: Status,
        var goTwitterFragment: Boolean = false,
        var searchViewVisibility: Boolean = true,
        var auth: Boolean = false
    ) : Data
}