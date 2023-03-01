package com.example.itouristui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itouristui.R

class ReviewsRecViewAdapter : RecyclerView.Adapter<ReviewsRecViewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(val view : View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.review_item,parent,false))
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 3
    }
}