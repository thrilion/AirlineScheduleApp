package com.example.igonzalez.airlinescheduleapp.ui.search

import com.example.igonzalez.airlinescheduleapp.api.AirlineSchedulesApi
import com.example.igonzalez.airlinescheduleapp.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchPresenter(searchView: SearchView) : BasePresenter<SearchView>(searchView) {

    private val CLIENT_ID = "n7t66rwvuxu5fv8pc7ng4evj"
    private val CLIENT_SECRET = "8dxqe3z6rm"
    private val GRANT_TYPE = "client_credentials"

    @Inject
    lateinit var airlineScheduleApi: AirlineSchedulesApi

    private var subscription: Disposable? = null

    fun makeAuthTokenRequest() {
        subscription = airlineScheduleApi.getAuthToken(
            CLIENT_ID,
            CLIENT_SECRET,
            GRANT_TYPE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result -> view.saveAuthToken(result.accessToken) }
    }

    fun disposeSubscription() {
        subscription?.dispose()
    }
}