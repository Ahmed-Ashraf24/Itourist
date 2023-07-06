package com.example.itouristui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itouristui.R
import com.example.itouristui.models.CityData
import com.example.itouristui.models.PlaceImportantData

import kotlinx.android.synthetic.main.places.view.ItemListPlaceNameTV
import kotlinx.android.synthetic.main.profile_tours_card.view.*
import java.util.ArrayList


class Places_adapter(var data: ArrayList<CityData>): RecyclerView.Adapter<Places_adapter.ViewHolder>() {
    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val placename= view.ItemListPlaceNameTV
        val toursTaken = view.numberOfToursInCardTextView
        val likes = view.numberOfLikesInCardTextView
        val pic = view.TourCityPicture


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val view= LayoutInflater.from(parent.context).inflate(R.layout.profile_tours_card,parent,false)
            return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.placename.text=data[position].name
        holder.likes.text = data[position].liked.toString()
        holder.toursTaken.text = data[position].tours.toString()
        Glide.with(holder.view.context).load(data[position].image).into(holder.pic)
    }

    override fun getItemCount(): Int=data.size

}