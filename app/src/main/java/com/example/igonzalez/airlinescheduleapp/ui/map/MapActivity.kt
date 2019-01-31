package com.example.igonzalez.airlinescheduleapp.ui.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.igonzalez.airlinescheduleapp.R
import com.example.igonzalez.airlinescheduleapp.model.Entities
import com.example.igonzalez.airlinescheduleapp.ui.base.BaseActivity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : BaseActivity<MapPresenter>(), MapView, OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

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

        val origin = intent.getStringExtra(INTENT_ORIGIN_AIRPORT)
            ?: throw IllegalStateException("field $INTENT_ORIGIN_AIRPORT missing in Intent")
        val destination = intent.getStringExtra(INTENT_DESTINATION_AIRPORT)
            ?: throw IllegalStateException("field $INTENT_DESTINATION_AIRPORT missing in Intent")
        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        ) ?: return
        val token = sharedPref.getString("API_ACCESS_TOKEN", "")

        presenter.makeOriginAirportLocationRequest(token, origin)
        presenter.makeDestinationAirportLocationRequest(token, destination)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun setPresenter(): MapPresenter {
        return MapPresenter(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    override fun showOriginAirport(originLocation: Entities.Coordinate) {
        val originAirport = LatLng(originLocation.latitude, originLocation.longitude)
        mMap.addMarker(MarkerOptions().position(originAirport).title("Origin Airport"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(originAirport))
    }

    override fun showDestinationAirport(destinationLocation: Entities.Coordinate) {
        val destinationAirport = LatLng(destinationLocation.latitude, destinationLocation.longitude)
        mMap.addMarker(MarkerOptions().position(destinationAirport).title("Destination Airport"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(destinationAirport))
    }

    override fun showError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        presenter.disposeSubscription()
    }
}
