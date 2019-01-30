package com.example.igonzalez.airlinescheduleapp.ui.base

import com.example.igonzalez.airlinescheduleapp.dagger.component.AirlineScheduleComponent
import com.example.igonzalez.airlinescheduleapp.dagger.component.DaggerAirlineScheduleComponent
import com.example.igonzalez.airlinescheduleapp.dagger.module.ContextModule
import com.example.igonzalez.airlinescheduleapp.ui.schedule.SchedulePresenter
import com.example.igonzalez.airlinescheduleapp.ui.search.SearchPresenter

abstract class BasePresenter<out V : BaseView>(protected val view: V) {

    private val component: AirlineScheduleComponent= DaggerAirlineScheduleComponent.builder()
        .contextModule(ContextModule(view))
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is SchedulePresenter -> component.inject(this)
        }
    }
}