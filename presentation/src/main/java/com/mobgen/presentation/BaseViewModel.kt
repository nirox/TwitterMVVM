package com.mobgen.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val status: MutableLiveData<Status> = MutableLiveData()

    init {
        status.postValue(Status.LOADING)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun executeUseCase(useCase: () -> Disposable) {
        compositeDisposable.add(useCase())
    }

    enum class Status {
        LOADING,
        LOADING_NEXT_PAGE,
        SUCCESS,
        ERROR
    }

}