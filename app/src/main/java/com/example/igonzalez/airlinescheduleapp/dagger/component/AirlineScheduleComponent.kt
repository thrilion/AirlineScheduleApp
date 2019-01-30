package com.example.igonzalez.airlinescheduleapp.dagger.component

import com.example.igonzalez.airlinescheduleapp.dagger.module.ContextModule
import com.example.igonzalez.airlinescheduleapp.dagger.module.NetworkModule
import com.example.igonzalez.airlinescheduleapp.ui.schedule.SchedulePresenter
import com.example.igonzalez.airlinescheduleapp.ui.search.SearchPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, NetworkModule::class])
interface AirlineScheduleComponent {

    fun inject(searchPresenter: SearchPresenter)
    fun inject(schedulePresenter: SchedulePresenter)

}