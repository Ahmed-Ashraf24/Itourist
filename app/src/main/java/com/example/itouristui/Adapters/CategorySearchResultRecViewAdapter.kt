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


class CategorySearchResultRecViewAdapter(var data: List<SearchResult>,val resId : Int, val displayItemListener : (SearchResult,Int)->(Unit)): RecyclerView.Adapter<CategorySearchResultRecViewAdapter.CategorySearchResultViewHolder>() {
    class CategorySearchResultViewHolder(val view: View): RecyclerView.ViewHolder(view){
        var placenameTV= view.ItemListPlaceNameTV
        var distanceTV =view.ItemListDistTextView
        val placeImg = view.place_image

        fun bind(data : SearchResult,resId:Int , clickListener : (SearchResult,Int)->(Unit) ){
            view.setOnClickListener{
                clickListener(data,resId)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorySearchResultViewHolder {
            return CategorySearchResultViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.places,parent,false))
    }
    override fun onBindViewHolder(holder: CategorySearchResultViewHolder, position: Int) {
        holder.placenameTV.text=data[position].poi?.names?.first() ?: "UnKnown"
        holder.distanceTV.text=data[position].distance?.inKilometers().toString().dropLast(5)+" Km"
        holder.placeImg.setImageResource(resId)
        holder.bind(data[position],resId,displayItemListener)
    }

    override fun getItemCount(): Int= data.size

}