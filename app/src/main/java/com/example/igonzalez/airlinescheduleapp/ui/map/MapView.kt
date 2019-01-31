package com.example.igonzalez.airlinescheduleapp.ui.map

import com.example.igonzalez.airlinescheduleapp.model.Entities
import com.example.igonzalez.airlinescheduleapp.ui.base.BaseView

interface MapView : BaseView {

    fun showOriginAirport(originLocation: Entities.Coordinate)

    fun showDestinationAirport(destinationLocation: Entities.Coordinate)

    fun showError(error: String?)
}