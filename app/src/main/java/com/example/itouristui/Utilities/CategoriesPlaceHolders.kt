package com.example.itouristui.Utilities

import com.example.itouristui.R
import com.example.itouristui.models.CategoriesOfPlaces

class CategoriesPlaceHolders {

    companion object{
         val categoriesOfPlaces = listOf(
            CategoriesOfPlaces("shopping", R.drawable.shopping_catrgory,"clothing"),
            CategoriesOfPlaces("restaurant", R.drawable.restaurent_category,"Restaurant"),
            CategoriesOfPlaces("market", R.drawable.market_catrgory,"market"),
            CategoriesOfPlaces("coffee", R.drawable.coffee_catrgory,"Café/Pub"),
            CategoriesOfPlaces("points of interest", R.drawable.points_of_interest_catrgory,"Tourist Attraction"),
            CategoriesOfPlaces("gardens", R.drawable.gardens_catrgory,"Park & Recreation Area "),
            CategoriesOfPlaces("studio", R.drawable.studio_catrgory,"Media Facility"),
            CategoriesOfPlaces("technological", R.drawable.technological_catrgory,"electrical"),
            CategoriesOfPlaces("hospital", R.drawable.hospital_catrgory,"hospital"),
            CategoriesOfPlaces("jewels", R.drawable.jewels_catrgory,"Jewelry, Clocks & Watches"),
            CategoriesOfPlaces("governmental", R.drawable.governmental_catrgory,"government"),
        )
    }
}