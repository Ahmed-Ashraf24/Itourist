package com.example.itouristui

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseObj {
    val auth = FirebaseAuth.getInstance()
    val fireStore = FirebaseFirestore.getInstance()
    var uid = ""

}