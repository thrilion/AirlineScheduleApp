package com.example.igonzalez.airlinescheduleapp.dagger.component

import com.example.igonzalez.airlinescheduleapp.dagger.module.ContextModule
import com.example.igonzalez.airlinescheduleapp.dagger.module.NetworkModule
import com.example.igonzalez.airlinescheduleapp.ui.schedule.SchedulePresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, NetworkModule::class])
interface AirlineScheduleComponent {

    fun inject(schedulePresenter: SchedulePresenter)

}