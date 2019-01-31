package com.example.igonzalez.airlinescheduleapp.ui.map

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import com.example.igonzalez.airlinescheduleapp.R
import com.example.igonzalez.airlinescheduleapp.model.Entities
import com.example.igonzalez.airlinescheduleapp.ui.base.BaseActivity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.*

class MapActivity : BaseActivity<MapPresenter>(), MapView, OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var destinationAirport: LatLng
    private lateinit var originAirport: LatLng

    companion object {
        private const val INTENT_ORIGIN_AIRPORT = "originAirport"
        private const val INTENT_DESTINATION_AIRPORT = "destinationAirport"

        fun newIntent(context: Context, originAirport: String, destinationAirport: String): Intent {
            val intent = Intent(context, MapActivity::class.java)
            intent.putExtra(INTENT_ORIGIN_AIRPORT, originAirport)
            intent.putExtra(INTENT_DESTINATION_AIRPORT, destinationAirport)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun setPresenter(): MapPresenter {
        return MapPresenter(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        requestOriginAirport()
    }

    private fun requestOriginAirport() {
        val origin = intent.getStringExtra(INTENT_ORIGIN_AIRPORT)
            ?: throw IllegalStateException("field $INTENT_ORIGIN_AIRPORT missing in Intent")
        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        ) ?: return
        val token = sharedPref.getString("API_ACCESS_TOKEN", "")

        presenter.makeOriginAirportLocationRequest(token, origin)
    }

    override fun showOriginAirport(originLocation: Entities.Coordinate) {
        originAirport = LatLng(originLocation.latitude, originLocation.longitude)
        googleMap.addMarker(MarkerOptions().position(originAirport).title("Origin Airport"))

        requestDestinationAirport()
    }

    private fun requestDestinationAirport() {
        val destination = intent.getStringExtra(INTENT_DESTINATION_AIRPORT)
            ?: throw IllegalStateException("field $INTENT_DESTINATION_AIRPORT missing in Intent")
        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        ) ?: return
        val token = sharedPref.getString("API_ACCESS_TOKEN", "")

        presenter.makeDestinationAirportLocationRequest(token, destination)
    }

    override fun showDestinationAirport(destinationLocation: Entities.Coordinate) {
        destinationAirport = LatLng(destinationLocation.latitude, destinationLocation.longitude)
        googleMap.addMarker(MarkerOptions().position(destinationAirport).title("Destination Airport"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(destinationAirport))

        configGoogleMapsView()
    }

    private fun configGoogleMapsView() {
        // polyline
        val pattern = Arrays.asList(Dash(30f), Gap(20f))
        googleMap.addPolyline(
            PolylineOptions()
                .add(originAirport, destinationAirport)
                .width(5f)
                .color(Color.RED)
                .geodesic(true)
                .pattern(pattern)
        )

        // camera
        val bounds = LatLngBounds.Builder()
            .include(originAirport)
            .include(destinationAirport)
            .build()
        val padding = 150
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        googleMap.moveCamera(cameraUpdate)
        googleMap.animateCamera(cameraUpdate)
    }

    override fun showError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        presenter.disposeSubscription()
    }
}
