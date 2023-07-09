package com.example.itouristui.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class TourOffer(
    val description : String?=null,
    val referenceTourGuide : DocumentReference?=null,
    val timestamp: Timestamp?=null,
    val tourAt : String?=null
)