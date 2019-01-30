package com.example.igonzalez.airlinescheduleapp.api

import com.example.igonzalez.airlinescheduleapp.model.Entities
import io.reactivex.Observable
import retrofit2.http.*

interface AirlineSchedulesApi {

    @GET("operations/schedules/{origin}/{destination}/{fromDateTime}?directFlights=1")
    fun getAirlineSchedules(
        @Header("Authorization") token: String,
        @Path("origin") origin: String,
        @Path("destination") destination: String,
        @Path("fromDateTime") fromDateTime: String
    ): Observable<Entities.ScheduleRequestResponse>

    @FormUrlEncoded
    @POST("oauth/token")
    fun getAuthToken(@Field("client_id") clientId: String,
                     @Field("client_secret") clientSecret: String,
                     @Field("grant_type") grantType: String
    ): Observable<Entities.TokenResponse>
}