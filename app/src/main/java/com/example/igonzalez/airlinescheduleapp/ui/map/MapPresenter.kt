package com.example.igonzalez.airlinescheduleapp.ui.map

import com.example.igonzalez.airlinescheduleapp.api.AirlineSchedulesApi
import com.example.igonzalez.airlinescheduleapp.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapPresenter(mapView: MapView) : BasePresenter<MapView>(mapView) {

    @Inject
    lateinit var airlineScheduleApi: AirlineSchedulesApi

    private var subscription: Disposable? = null

    fun makeOriginAirportLocationRequest(token: String, airportCode: String) {
        subscription = airlineScheduleApi.getAirportInfo(token, airportCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> view.showOriginAirport(result.airportResource.airports.airport.position.coordinate) },
                { error -> view.showError(error.message) }
            )
    }

    fun makeDestinationAirportLocationRequest(token: String, airportCode: String) {
        subscription = airlineScheduleApi.getAirportInfo(token, airportCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> view.showDestinationAirport(result.airportResource.airports.airport.position.coordinate) },
                { error -> view.showError(error.message) }
            )
    }

    fun disposeSubscription() {
        subscription?.dispose()
    }
}