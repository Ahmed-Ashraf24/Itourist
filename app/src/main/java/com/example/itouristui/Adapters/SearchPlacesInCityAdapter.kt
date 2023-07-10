package com.example.itouristui.Adapters

import android.app.appsearch.SearchResults
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itouristui.R
import com.tomtom.sdk.search.model.result.SearchResult
import kotlinx.android.synthetic.main.inflated_search_place.view.*

class SearchPlacesInCityAdapter(val results : List<SearchResult>, val selectedListener : (SearchResult)->(Unit)): RecyclerView.Adapter<SearchPlacesInCityAdapter.SearchPlacesViewHolder>() {

    inner class SearchPlacesViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        val placeNameView = view.SearchResPlaceNameTextView
        val cityCountryView = view.SearchResCityCountryNameTextView
        val distanceView = view.SearchResDistanceTextView

        fun bind(searchResult: SearchResult, clickListener : (SearchResult)->(Unit)){
            itemView.setOnClickListener {
                clickListener(searchResult)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPlacesViewHolder {
        return SearchPlacesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.inflated_search_place,parent,false))
    }

    override fun onBindViewHolder(holder: SearchPlacesViewHolder, position: Int) {
        holder.placeNameView.text = results[position].poi?.names?.first()?:"Unknown"
        holder.cityCountryView.text = results[position].address?.run {
             "$streetName, $localName, $country"
        }?:"Unknown Address"
        holder.distanceView.text = results[position].distance?.inKilometers().toString().dropLast(5)+"Km"

        holder.bind(results[position],selectedListener)
    }

    override fun getItemCount(): Int {
        return results.size
    }
}