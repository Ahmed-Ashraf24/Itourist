package com.example.itouristui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itouristui.R
import com.example.itouristui.models.places
import com.tomtom.sdk.search.model.result.SearchResult
import kotlinx.android.synthetic.main.places.view.*
import java.util.ArrayList


class CategorySearchResultRecViewAdapter(var data: List<SearchResult>, val displayItemListener : (SearchResult)->(Unit)): RecyclerView.Adapter<CategorySearchResultRecViewAdapter.CategorySearchResultViewHolder>() {
    class CategorySearchResultViewHolder(val view: View): RecyclerView.ViewHolder(view){
        var placenameTV= view.ItemListPlaceNameTV
        var distanceTV =view.ItemListDistTextView

        fun bind(data : SearchResult , clickListener : (SearchResult)->(Unit) ){
            view.setOnClickListener{
                clickListener(data)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorySearchResultViewHolder {
            return CategorySearchResultViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.places,parent,false))
    }
    override fun onBindViewHolder(holder: CategorySearchResultViewHolder, position: Int) {
        holder.placenameTV.text=data[position].poi?.names?.first() ?: "UnKnown"
        holder.distanceTV.text=data[position].distance?.inKilometers().toString().dropLast(5)+" Km"

        holder.bind(data[position],displayItemListener)
    }

    override fun getItemCount(): Int= data.size

}