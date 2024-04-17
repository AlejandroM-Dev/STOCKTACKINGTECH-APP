package com.example.stocktakingtech.Model

import com.google.android.gms.maps.model.LatLng

data class Location (
        val lat: Double, val lng: Double
){
    fun toLatLng(): LatLng{
        return LatLng(lat, lng)
    }
}