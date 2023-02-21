package com.example.itouristui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.itouristui.R
import com.example.itouristui.models.CityDetails
import kotlinx.android.synthetic.main.country_city_search_autocomplete.view.*

class CitiesSearchRecViewAdapter(val cities : List<CityDetails>) : RecyclerView.Adapter<CitiesSearchRecViewAdapter.CitySearchViewHolder>() {

    class CitySearchViewHolder(val view : View) : ViewHolder(view){
        val cityNameTextView = view.SearchResCityNameTextView
        val countryNameTextView = view.SearchResCountryNameTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitySearchViewHolder {
        return CitySearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.country_city_search_autocomplete,parent,false))
    }

    override fun onBindViewHolder(holder: CitySearchViewHolder, position: Int) {
        holder.cityNameTextView.text = cities[position].name
        holder.countryNameTextView.text = cities[position].country.name
    }

    override fun getItemCount(): Int {
        return cities.size
    }
}