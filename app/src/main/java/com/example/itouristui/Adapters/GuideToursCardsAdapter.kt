package com.example.itouristui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itouristui.R
import com.example.itouristui.models.PlaceImportantData
import kotlinx.android.synthetic.main.places.view.ItemListDistTextView
import kotlinx.android.synthetic.main.places.view.ItemListPlaceNameTV
import java.util.ArrayList

class GuideToursCardsAdapter(var data: ArrayList<PlaceImportantData>): RecyclerView.Adapter<GuideToursCardsAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var placename = view.ItemListPlaceNameTV
        var distance = view.ItemListDistTextView

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view= LayoutInflater.from(parent.context).inflate(R.layout.profile_tours_card,parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.placename.text=data[position].nameOfPlace
        holder.distance.text=data[position].distanceAway
    }

    override fun getItemCount(): Int=data.size
}