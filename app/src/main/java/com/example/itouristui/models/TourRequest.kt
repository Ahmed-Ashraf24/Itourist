package com.example.itouristui.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class TourRequest(
    val createdAt :Timestamp?=null,
    val numberOfPeople : Int?=null,
    val arrivalDate : String?=null,
    val rangeOfBudget : String? = null,
    val description : String?=null,
    val tourGuideGender : String?=null,
    val spokenLanguages : String?=null,
    val ownsVehicle : String?=null,
    val status : String?=null,
    val userRef : DocumentReference?=null,
    val numberOfOffers : Int? = null
)