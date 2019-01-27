package com.example.igonzalez.airlinescheduleapp.ui.schedule

import com.example.igonzalez.airlinescheduleapp.model.Entities
import com.example.igonzalez.airlinescheduleapp.ui.base.BaseView

interface ScheduleView : BaseView {

    fun showSchedules(schedules: List<Entities.Schedule>)

    fun printDuration(duration: String)

    fun showLoading()

    fun hideLoading()

    fun showError(error: String?)
}