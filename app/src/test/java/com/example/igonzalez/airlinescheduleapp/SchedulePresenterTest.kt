package com.example.igonzalez.airlinescheduleapp

import com.example.igonzalez.airlinescheduleapp.api.AirlineSchedulesApi
import com.example.igonzalez.airlinescheduleapp.model.Entities
import com.example.igonzalez.airlinescheduleapp.ui.schedule.SchedulePresenter
import com.example.igonzalez.airlinescheduleapp.ui.schedule.ScheduleView
import io.reactivex.Observable
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins

class SchedulePresenterTest {

    @Mock
    private val airlineSchedulesApi: AirlineSchedulesApi? = null

    @Mock
    private lateinit var scheduleView: ScheduleView

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun makeScheduleRequest_InvokesCorrectApiRequest() {
        // Given
        val scheduleList = scheduleList()
        `when`(airlineSchedulesApi?.getAirlineSchedules(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Observable.just(scheduleList))
        val schedulePresenter = SchedulePresenter(scheduleView)

        // When
        schedulePresenter.makeScheduleRequest("token", "origin", "destination", "date")

        // Then
        val inOrder = Mockito.inOrder(scheduleView)
        inOrder.verify(scheduleView).showLoading()
        inOrder.verify(scheduleView).showSchedules(scheduleList.scheduleResource.schedules)
        inOrder.verifyNoMoreInteractions()
    }

    private fun scheduleList() : Entities.ScheduleRequestResponse {
        val scheduledTimeLocal = Entities.ScheduledTimeLocal("2019-01-29 07:40")
        val arrival = Entities.Arrival("FRA", scheduledTimeLocal)
        val departure = Entities.Departure("ZRH", scheduledTimeLocal)
        val flight = Entities.Flight(departure, arrival)
        val totalJourney = Entities.TotalJourney("1h 30min")
        val schedule = Entities.Schedule(totalJourney, flight)
        val scheduleResource = Entities.ScheduleResource(listOf(schedule, schedule))
        return Entities.ScheduleRequestResponse(scheduleResource)
    }
}