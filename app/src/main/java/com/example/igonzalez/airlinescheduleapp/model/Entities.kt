package com.example.igonzalez.airlinescheduleapp.model

import com.squareup.moshi.Json

object Entities {

    /*
    * SCHEDULE REQUEST
    */

    data class ScheduleRequestResponse(@field:Json(name = "ScheduleResource") val scheduleResource: ScheduleResource)

    data class ScheduleResource(@field:Json(name = "Schedule") val schedules: List<Schedule>)

    data class Schedule(
        @field:Json(name = "TotalJourney") val totalJourney: TotalJourney,
        @field:Json(name = "Flight") val flight: Flight
    )

    data class TotalJourney(@field:Json(name = "Duration") val duration: String)

    data class Flight(
        @field:Json(name = "Departure") val departure: Departure,
        @field:Json(name = "Arrival") val arrival: Arrival
    )

    data class Departure(
        @field:Json(name = "AirportCode") val airportCode: String,
        @field:Json(name = "ScheduledTimeLocal") val scheduledTimeLocal: ScheduledTimeLocal
    )

    data class Arrival(
        @field:Json(name = "AirportCode") val airportCode: String,
        @field:Json(name = "ScheduledTimeLocal") val scheduledTimeLocal: ScheduledTimeLocal
    )

    data class ScheduledTimeLocal(@field:Json(name = "DateTime") val dateTime: String)

    /*
    * AIRPORT REQUEST
    */

    data class AirportResourceResponse(@field:Json(name = "AirportResource") val airportResource: AirportResource)

    data class AirportResource(@field:Json(name = "Airports") val airports: Airports)

    data class Airports(@field:Json(name = "Airport") val airport: Airport)

    data class Airport(@field:Json(name = "Position") val position: Position)

    data class Position(@field:Json(name = "Coordinate") val coordinate: Coordinate)

    data class Coordinate(@field:Json(name = "Latitude") val latitude: Double,
                          @field:Json(name = "Longitude") val longitude: Double
    )

    /*
    * TOKEN REQUEST
    */

    data class TokenResponse(@field:Json(name = "access_token") val accessToken: String)
}