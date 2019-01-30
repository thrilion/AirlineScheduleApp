package com.example.igonzalez.airlinescheduleapp.model

import com.squareup.moshi.Json

object Entities {
    data class ScheduleRequestResponse(@field:Json(name = "ScheduleResource") val scheduleResource: ScheduleResource)

    data class ScheduleResource(@field:Json(name = "Schedule") val schedules: List<Schedule>)

    data class Schedule(
        @field:Json(name = "TotalJourney") val totalJourney: TotalJourney,
        @field:Json(name = "Flight") val flight: Flight
    )

    data class TotalJourney(@field:Json(name = "Duration") val duration: String)

    data class Flight(
        @field:Json(name = "Departure") val departure: Departure,
        @field:Json(name = "Arrival") val arrival: Arrival,
        @field:Json(name = "Details") val details: Details
    )

    data class Departure(
        @field:Json(name = "AirportCode") val airportCode: String,
        @field:Json(name = "ScheduledTimeLocal") val scheduledTimeLocal: ScheduledTimeLocal,
        @field:Json(name = "Terminal") val terminal: Terminal
    )

    data class Arrival(
        @field:Json(name = "AirportCode") val airportCode: String,
        @field:Json(name = "ScheduledTimeLocal") val scheduledTimeLocal: ScheduledTimeLocal,
        @field:Json(name = "Terminal") val terminal: Terminal
    )

    data class ScheduledTimeLocal(@field:Json(name = "DateTime") val dateTime: String)

    data class Terminal(@field:Json(name = "Name") val name: String)

    data class Details(@field:Json(name = "Stops") val stops: Stops)

    data class Stops(@field:Json(name = "StopQuantity") val stopQuantity: String)

    data class TokenResponse(@field:Json(name = "access_token") val accessToken: String)
}