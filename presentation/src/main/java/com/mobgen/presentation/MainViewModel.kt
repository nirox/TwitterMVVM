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
        data.value = mainViewData
        executeUseCase {
            authenticate.execute().subscribe(
                executor = AndroidSchedulers.mainThread(),
                onComplete = {
                    data.postValue(mainViewData.apply { status = Status.SUCCESS })
                },
                onError = {
                    data.postValue(mainViewData.apply { status = Status.ERROR })
                    throw  it
                }
            )
        }
    }

    class MainViewData(
        override var status: Status
    ) : Data
}