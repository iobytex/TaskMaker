package com.iobytex.extensions

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun createLocationRequest(): LocationRequest =  LocationRequest.create().apply {
    interval = 10000
    fastestInterval = 5000
    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
}


@SuppressLint("MissingPermission")
fun FusedLocationProviderClient.locationFlow() : Flow<Location> = callbackFlow<Location> {

    val callback = object  : LocationCallback(){
        override fun onLocationResult(result: LocationResult?) {
            result ?: return
            try {
                trySend(result.lastLocation).isSuccess
            }catch (e: Exception){

            }
        }
    }


    requestLocationUpdates(createLocationRequest(),callback, Looper.getMainLooper())
        .addOnFailureListener { e ->
        close(e)
    }

    awaitClose {
        removeLocationUpdates(callback)
    }
}