package com.example.itouristui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itouristui.R
import kotlin.math.min

class GeneralPageRecViewAdapter(val dataSet : List<String>): RecyclerView.Adapter<GeneralPageRecViewAdapter.GeneralPageViewHolder>() {

    class GeneralPageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralPageViewHolder {
        return GeneralPageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.suggested_places, parent, false))

    }

    override fun onBindViewHolder(holder: GeneralPageViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return min(5,10)
    }
}