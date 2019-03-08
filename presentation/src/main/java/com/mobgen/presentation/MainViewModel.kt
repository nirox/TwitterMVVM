package com.mobgen.presentation

import com.mobgen.domain.subscribe
import com.mobgen.domain.useCase.Authenticate
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val authenticate: Authenticate) : BaseViewModel() {

    fun authenticate() {
        status.value = Status.LOADING
        executeUseCase {
            authenticate.execute().subscribe(
                executor = AndroidSchedulers.mainThread(),
                onComplete = {
                    status.postValue(BaseViewModel.Status.SUCCESS)
                },
                onError = {
                    status.postValue(BaseViewModel.Status.ERROR)
                    throw  it
                }
            )
        }
    }
}