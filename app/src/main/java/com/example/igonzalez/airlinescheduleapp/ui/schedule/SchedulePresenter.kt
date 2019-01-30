package com.example.igonzalez.airlinescheduleapp.ui.schedule

import android.util.Log
import com.example.igonzalez.airlinescheduleapp.ui.base.BasePresenter
import com.example.igonzalez.airlinescheduleapp.api.AirlineSchedulesApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulePresenter(scheduleView: ScheduleView) : BasePresenter<ScheduleView>(scheduleView) {

    @Inject
    lateinit var airlineScheduleApi: AirlineSchedulesApi

    private var subscription: Disposable? = null

    fun makeScheduleRequest(origin: String, destination: String, fromDateTime: String) {
        view.showLoading()
        subscription = airlineScheduleApi.getAirlineSchedules(origin, destination, fromDateTime)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate { view.hideLoading() }
            .subscribe(
                { result -> view.showSchedules(result.scheduleResource.schedules)},
                { error -> view.showError(error.message) }
            )
    }

    fun disposeSubscription() {
        subscription?.dispose()
    }
}