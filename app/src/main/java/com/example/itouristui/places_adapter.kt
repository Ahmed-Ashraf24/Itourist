package com.example.itouristui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itouristui.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.places.view.*
import java.util.ArrayList


class places_adapter(var data: ArrayList<places>): RecyclerView.Adapter<places_adapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var placename= view.place_name
        var worktime=view.work_time
        var distance =view.distance

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): places_adapter.ViewHolder {

            val view= LayoutInflater.from(parent.context).inflate(R.layout.places,parent,false)
            return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.placename.text=data[position].Name
        holder.worktime.text=data[position].worktime
        holder.distance.text=data[position].away
    }

    override fun getItemCount(): Int=data.size

}