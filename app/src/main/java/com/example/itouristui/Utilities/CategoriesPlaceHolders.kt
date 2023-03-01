package com.example.itouristui.Utilities

import com.example.itouristui.R
import com.example.itouristui.models.CategoriesOfPlaces

class CategoriesPlaceHolders {

    companion object{
         val categoriesOfPlaces = listOf(
            CategoriesOfPlaces("shopping", R.drawable.shopping_illustr_undraw,"clothing"),
            CategoriesOfPlaces("restaurant", R.drawable.restaurent_illustr_undraw,"Restaurant"),
            CategoriesOfPlaces("market", R.drawable.markets_illustr_undraw,"market"),
            CategoriesOfPlaces("coffee", R.drawable.coffe_illustr_undraw,"Café/Pub"),
            CategoriesOfPlaces("points of interest", R.drawable.poi_illustr_undraw,"Tourist Attraction"),
            CategoriesOfPlaces("gardens", R.drawable.gardens_illustr_undraw,"Park & Recreation Area "),
            CategoriesOfPlaces("studio", R.drawable.studio_illustr_undraw,"Media Facility"),
            CategoriesOfPlaces("technological", R.drawable.electronics_illustr_undraw,"electrical"),
            CategoriesOfPlaces("hospital", R.drawable.hospital_illustr_undraw,"hospital"),
            CategoriesOfPlaces("jewels", R.drawable.jewllery_illust_undraw,"Jewelry, Clocks & Watches"),
            CategoriesOfPlaces("governmental", R.drawable.govenmental_illust_undraw,"government"),
        )
    }
}