package com.example.itouristui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itouristui.R

class OfferRecyclerViewAdapter : RecyclerView.Adapter<OfferRecyclerViewAdapter.OffersViewHolder>() {

    class OffersViewHolder(val view : View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersViewHolder {
        return OffersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.inflated_offer,parent,false))
    }

    override fun onBindViewHolder(holder: OffersViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 4
    }
}