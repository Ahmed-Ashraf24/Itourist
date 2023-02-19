package com.example.itouristui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserPlainData(
    val fullName : String,
    val email : String,
    val phoneNumber : String,
    val dataOfBirth : String
) : Parcelable