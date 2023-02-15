package com.example.itouristui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itouristui.R
import com.example.itouristui.models.CategoriesOfPlaces
import kotlinx.android.synthetic.main.categories_placeholders_items_search_xml.view.*
import java.util.*

class CategoriesPlaceHolderRecViewAdapter(val categories : List<CategoriesOfPlaces>) : RecyclerView.Adapter<CategoriesPlaceHolderRecViewAdapter.PopularSearchViewHolder>() {

   class PopularSearchViewHolder(view : View) : RecyclerView.ViewHolder(view){
       val categoryNameView = view.CategoryPlaceHolderTextView
       val categoryImageView = view.CategoryPlaceHolderImageView

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularSearchViewHolder {
        return PopularSearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.categories_placeholders_items_search_xml , parent , false))
    }

    override fun onBindViewHolder(holder: PopularSearchViewHolder, position: Int) {
        holder.categoryNameView.text = categories[position].categoryName.capitalize(Locale.getDefault())
        holder.categoryImageView.setImageResource(categories[position].imageResId)
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}