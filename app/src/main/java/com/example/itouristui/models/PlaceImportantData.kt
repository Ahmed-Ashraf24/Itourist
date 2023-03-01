package com.example.itouristui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaceImportantData(
    val nameOfPlace : String,
    val address : String,
    val distanceAway : String,
    val lat : Double,
    val lon : Double
) : Parcelable