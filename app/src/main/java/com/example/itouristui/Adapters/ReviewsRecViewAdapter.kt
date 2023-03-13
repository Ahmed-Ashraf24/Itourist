package com.example.itouristui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itouristui.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.review_item.view.*

class ReviewsRecViewAdapter() : RecyclerView.Adapter<ReviewsRecViewAdapter.ReviewViewHolder>() {
    var likeDislikeNotAllowed = false

    constructor(likeDislikeNotAllowed : Boolean):this(){
        this.likeDislikeNotAllowed = likeDislikeNotAllowed
    }

    inner class ReviewViewHolder(val view : View) : RecyclerView.ViewHolder(view){



        fun removeLikeDislike(){
            if (likeDislikeNotAllowed){
                view.apply {
                    ReviewItemLikeButton.visibility = View.INVISIBLE
                    ReviewItemDislikeButton.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.review_item,parent,false))
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.removeLikeDislike()
    }

    override fun getItemCount(): Int {
        return 3
    }
}