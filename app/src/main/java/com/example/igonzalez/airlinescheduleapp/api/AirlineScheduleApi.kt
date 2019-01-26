package com.example.igonzalez.airlinescheduleapp.api

import com.example.igonzalez.airlinescheduleapp.model.Entities
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface AirlineSchedulesApi {

    @GET("operations/schedules/{origin}/{destination}/{fromDateTime}?directFlights=1")
    fun getAirlineSchedules(
        @Path("origin") origin: String,
        @Path("destination") destination: String,
        @Path("fromDateTime") fromDateTime: String
    ): Observable<Entities.ScheduleRequestResponse>

}