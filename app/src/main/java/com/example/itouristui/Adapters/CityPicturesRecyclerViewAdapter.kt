package com.example.itouristui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itouristui.R
import kotlinx.android.synthetic.main.inflated_city_picture.view.*

class CityPicturesRecyclerViewAdapter(val pictures : List<String> , val display : (String)->(Unit)) : RecyclerView.Adapter<CityPicturesRecyclerViewAdapter.CityPicturesViewHolder>() {

    class CityPicturesViewHolder(val view : View): RecyclerView.ViewHolder(view){
        val picture = view.CityOtherImage
        fun bind(pic : String , displayFun : (String)->(Unit)){
            view.setOnClickListener {
                displayFun(pic)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityPicturesViewHolder {
        return CityPicturesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.inflated_city_picture,parent,false))
    }

    override fun onBindViewHolder(holder: CityPicturesViewHolder, position: Int) {
        Glide.with(holder.view.context).load(pictures[position]).into(holder.picture)
        holder.bind(pictures[position],display)
    }

    override fun getItemCount(): Int {
        return pictures.size.let { if (it>8) 8 else it}
    }
}