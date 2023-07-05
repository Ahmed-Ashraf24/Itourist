package com.example.itouristui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itouristui.R
import com.example.itouristui.models.ReviewData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.review_item.view.*

class ReviewsRecViewAdapter(val reviews : List<ReviewData>) : RecyclerView.Adapter<ReviewsRecViewAdapter.ReviewViewHolder>() {


    class ReviewViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        val nameView = view.ReviewerNameTextView
        val locationView = view.reviewlocation
        val textReview = view.ReviewTextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.review_item,parent,false))
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.nameView.text = reviews[position].reviewerName
        holder.locationView.text = reviews[position].reviewerLocation
        holder.textReview.text = reviews[position].review
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
}