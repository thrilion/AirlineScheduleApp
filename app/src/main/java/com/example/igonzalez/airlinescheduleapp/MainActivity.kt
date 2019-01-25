package com.example.igonzalez.airlinescheduleapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.igonzalez.airlinescheduleapp.api.AirlineSchedulesApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    private val airlineScheduleApi by lazy {
        AirlineSchedulesApi.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_search.setOnClickListener {
                beginSearch()
        }
    }

    private fun beginSearch() {
        disposable = airlineScheduleApi.getAirlineSchedules("ZRH", "FRA", "2019-01-23")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> Log.e("JSON Response", result.scheduleResource.schedules[0].totalJourney.duration)},
                { error -> Log.e("Error Message", error.message) }
            )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
