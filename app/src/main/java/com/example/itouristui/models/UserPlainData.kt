package com.example.itouristui.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserPlainData(
    val uid : String?=null,
    val fullName : String?=null,
    val email : String?=null,
    val phoneNumber : String?=null,
    val dataOfBirth : String?=null,
    val gender : String?=null,
    val profileImg : String?=null,
    val country : String?=null,
    val city : String?=null
) : Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }
}