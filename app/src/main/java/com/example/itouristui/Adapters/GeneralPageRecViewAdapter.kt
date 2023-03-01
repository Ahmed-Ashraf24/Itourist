package com.example.itouristui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itouristui.Data.Remote.PicturesAPI
import com.example.itouristui.R
import com.example.itouristui.Utilities.CustomRetrofitCallBack
import com.tomtom.sdk.search.model.result.SearchResult
import kotlinx.android.synthetic.main.suggested_places.view.*
import kotlin.math.min

class GeneralPageRecViewAdapter(val dataSet : List<SearchResult> , val selectedListener : (SearchResult)->(Unit)): RecyclerView.Adapter<GeneralPageRecViewAdapter.GeneralPageViewHolder>() {

    class GeneralPageViewHolder(val itemView: View,val context: Context): RecyclerView.ViewHolder(itemView) {
        val placeNameTextView = itemView.generalItemPlaceNameTV
        val distanceAwayTextView = itemView.generalItemDistTextView
        val itemPicture = itemView.generalItemPictureIV

        fun bind(searchResult: SearchResult, clickListener : (SearchResult)->(Unit)){
            itemView.setOnClickListener {
                clickListener(searchResult)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralPageViewHolder {
        return GeneralPageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.suggested_places, parent, false),parent.context)

    }

    override fun onBindViewHolder(holder: GeneralPageViewHolder, position: Int) {
        holder.placeNameTextView.text = dataSet[position].poi?.names?.first()?:"Unknown"
        holder.distanceAwayTextView.text = dataSet[position].distance?.inKilometers().toString().dropLast(5)+"Km"
        holder.bind(dataSet[position],selectedListener)

        val city = dataSet[position].address?.localName?.filter {
            it!=' '}.toString()
        PicturesAPI.pictureApiInterface.getCities(dataSet[position].poi?.names?.first()+"in "+city).enqueue(
            CustomRetrofitCallBack<String>{
                it.body()?.let{pic->
                    Glide.with(holder.context).load(pic.trim('"')).into(holder.itemPicture)
                }
            }
        )
    }

    override fun getItemCount(): Int {
        return min(10,dataSet.size)
    }
}