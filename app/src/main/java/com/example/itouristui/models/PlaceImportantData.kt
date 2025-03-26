package com.example.itouristui.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
 data class PlaceImportantData(
    val id : String,
    val nameOfPlace : String,
    val address : String,
    val distanceAway : String,
    val lat : Double,
    val lon : Double
) : Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }
}