package com.example.itouristui

import com.example.itouristui.models.CityDetails
import com.example.itouristui.models.SimpleCityDetail

object iToursit {
    val selectedCities = ArrayList<SimpleCityDetail>()
    var newSelectedCity = false

    val lastAddedCity : SimpleCityDetail
        get()= selectedCities[selectedCities.lastIndex]

}