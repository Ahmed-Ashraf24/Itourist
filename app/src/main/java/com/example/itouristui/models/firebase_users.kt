package com.example.itouristui.models

data class firebase_users(val email : String,val name : String, val pic : String,val status : String){
    constructor() : this("", "","","") {}
}
