package com.example.stocktakingtech.View

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stocktakingtech.IO.ApiService
import com.example.stocktakingtech.IO.response.DistanceResponse
import com.example.stocktakingtech.Model.Step
import com.example.stocktakingtech.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_ruta_map.*
import retrofit2.Call
import retrofit2.Response


class RutaMap : AppCompatActivity(), OnMapReadyCallback {

    companion object{
        private const val REQUEST_CODE_AUTOCOMPLETE_FROM = 1
        private const val REQUEST_CODE_AUTOCOMPLETE_TO = 2
        private const val TAG = "RutaMap"
    }

    private lateinit var mMap: GoogleMap

    private var mMarkerFrom : Marker? = null
    private var mMarkerTo : Marker? = null
    private var mFromLatLng: LatLng? = null
    private var mToLatLng: LatLng? = null

    private var lastPolyline: Polyline? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruta_map)

        setupMap()
        setupPlaces()
    }

    private fun setupMap(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupPlaces(){
        Places.initialize(application, getString(R.string.android_sdk_places_api_key))

        editFrom.text = "Direccion origen: Sin seleccionar"
        editTo.text = "Direccion origen: Sin seleccionar"

        btnCambiarFrom.setOnClickListener{
            startAutocomplete(REQUEST_CODE_AUTOCOMPLETE_FROM)
        }

        btnCambiarTo.setOnClickListener {
            startAutocomplete(REQUEST_CODE_AUTOCOMPLETE_TO)
        }

        btnVolverLstProductoAInicio.setOnClickListener {
            val i = Intent(applicationContext, Inicio::class.java)
            startActivity(i)
        }
    }

    private fun startAutocomplete(requestCode : Int){
        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)

        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(this)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE_FROM) {
           processAutocompleteResult(resultCode, data){ place ->
               editFrom.text = getString(R.string.label_from, place.address)
               place.latLng?.let {
                   mFromLatLng = it
                   setMarkerFrom(it)
               }
           }
            return
        }else if(requestCode == REQUEST_CODE_AUTOCOMPLETE_TO){
            processAutocompleteResult(resultCode, data){ place ->
                editTo.text = getString(R.string.label_to, place.address)
                place.latLng?.let {
                    mToLatLng = it
                    setMarkerTo(it)
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun processAutocompleteResult (
            resultCode: Int, data: Intent?,
            callback: (Place)->Unit
    ){
        Log.d(TAG, "processAutocompleteResult(resultCode=$resultCode)")
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.let {
                    val place = Autocomplete.getPlaceFromIntent(data)
                    Log.i(TAG, "Place : $place")
                    callback(place)
                }
            }
            AutocompleteActivity.RESULT_ERROR -> {
                // TODO: Handle the error.
                data?.let {
                    val status = Autocomplete.getStatusFromIntent(data)
                    status.statusMessage?.let {
                        message -> Log.i(TAG, message)
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMinZoomPreference(10f)
        mMap.setMaxZoomPreference(20f)
    }

    private fun addMarker(latLng: LatLng, title: String): Marker {
        val markerOptions = MarkerOptions()
                .position(latLng)
                .title(title)

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

        return mMap.addMarker(markerOptions)
    }

    private fun setMarkerFrom(latLng: LatLng){
        mMarkerFrom?.remove()
        mMarkerFrom = addMarker(latLng, getString(R.string.marker_title_from))
        computeTravelInfo()
    }

    private fun setMarkerTo(latLng: LatLng){
        mMarkerTo?.remove()
        mMarkerTo = addMarker(latLng, getString(R.string.marker_title_to))
        computeTravelInfo()
    }

    private fun computeTravelInfo(){


        val from = mFromLatLng?.let { latLngToStr(it) }
        val to = mToLatLng?.let { latLngToStr(it) }


        if (from == null || to == null){
            return
        }
        Toast.makeText(applicationContext, "pasa", Toast.LENGTH_LONG).show();
        ApiService.create().getDistance(from, to).enqueue(object: retrofit2.Callback<DistanceResponse> {
            override fun onResponse(call: Call<DistanceResponse>, response: Response<DistanceResponse>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        displayTravelInfo(it)
                    }
                }else{
                    Log.d(TAG, "Unexpected response from to server")
                }
            }

            override fun onFailure(call: Call<DistanceResponse>, t: Throwable) {
                Log.d(TAG, t.localizedMessage ?: "")
            }

        })
    }

    private fun displayTravelInfo(distanceResponse: DistanceResponse){
        Toast.makeText(this, "llega", Toast.LENGTH_LONG);
        tvDistance.text = distanceResponse.distance.toString()
        tvTime.text = distanceResponse.time

        drawRoute(distanceResponse.steps)
    }

    private fun drawRoute(steps: List<Step>){
        Log.d(TAG, "drawRoute(...)")
        lastPolyline?.remove()
        if(steps == null || steps.isEmpty()){
            Log.d(TAG, "Couldn't draw route because steps were not provided")
            return
        }
        val options = PolylineOptions().clickable(true)
        options.add(steps[0].start_location.toLatLng())
        steps.forEach{
            options.add(it.end_location.toLatLng())
        }

        lastPolyline = mMap.addPolyline(options)
    }

    private fun latLngToStr(latLng: LatLng) ="${latLng.latitude}, ${latLng.longitude}"

}