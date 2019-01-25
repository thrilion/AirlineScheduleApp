package com.example.igonzalez.airlinescheduleapp.api

import com.example.igonzalez.airlinescheduleapp.model.Entities
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface AirlineSchedulesApi {

    @GET("operations/schedules/{origin}/{destination}/{fromDateTime}?directFlights=0")
    fun getAirlineSchedules(
        @Path("origin") origin: String,
        @Path("destination") destination: String,
        @Path("fromDateTime") fromDateTime: String
    ): Observable<Entities.ScheduleRequestResponse>

    companion object {
        fun create(): AirlineSchedulesApi {
            val retrofit = Retrofit.Builder()
                .client(getClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("https://api.lufthansa.com/v1/")
                .build()

            return retrofit.create(AirlineSchedulesApi::class.java)
        }

        private fun getClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val builder = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + "3rwku8vq838t5z5g5an4x8t2")
                        .build()
                    chain.proceed(newRequest)
                }

            return builder.build()
        }
    }
}