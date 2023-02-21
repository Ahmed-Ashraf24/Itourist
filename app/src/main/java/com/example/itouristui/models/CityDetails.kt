package com.example.itouristui.models

data class CityDetails(
    val adminDivision1: AdminDivision1,
    val coordinates: Coordinates,
    val country: Country,
    val elevation: Int,
    val geonameId: Int,
    val id: String,
    val name: String,
    val population: Int,
    val score: Double,
    val timezoneId: String,
    val type: String
)

data class AdminDivision1(
    val geonameId: Int,
    val id: String,
    val name: String
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double
)

data class Country(
    val geonameId: Int,
    val id: String,
    val name: String
)