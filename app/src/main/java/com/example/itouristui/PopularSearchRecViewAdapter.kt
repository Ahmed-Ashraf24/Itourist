package com.example.itouristui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.items_of_featured_search_xml.view.*

class PopularSearchRecViewAdapter(val popularSearchNames : List<String>) : RecyclerView.Adapter<PopularSearchRecViewAdapter.PopularSearchViewHolder>() {

   class PopularSearchViewHolder(view : View) : RecyclerView.ViewHolder(view){
       val itemNameView = view.PopularSearchItemName

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularSearchRecViewAdapter.PopularSearchViewHolder {
        return PopularSearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_of_featured_search_xml , parent , false))
    }

    override fun onBindViewHolder(holder: PopularSearchRecViewAdapter.PopularSearchViewHolder, position: Int) {
        holder.itemNameView.text = popularSearchNames[position]
    }

    override fun getItemCount(): Int {
        return popularSearchNames.size
    }
}