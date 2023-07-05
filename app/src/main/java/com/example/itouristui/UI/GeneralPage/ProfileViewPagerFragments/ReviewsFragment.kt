package com.example.itouristui.UI.GeneralPage.ProfileViewPagerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.ReviewsRecViewAdapter
import com.example.itouristui.FirebaseObj
import com.example.itouristui.R
import com.example.itouristui.models.ReviewData
import com.example.itouristui.models.UserPlainData
import kotlinx.android.synthetic.main.fragment_reviews.*

class ReviewsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseObj.fireStore.collection("Users").document(FirebaseObj.uid).run {
            val reviewList = ArrayList<ReviewData>()
            get().addOnSuccessListener {
                val myData = it.toObject(UserPlainData::class.java)
                collection("Places Reviews")
                    .get().addOnSuccessListener {reviews->
                        for(revSnap in reviews){
                            val review = ReviewData(myData!!.fullName,"${myData.city},${myData.country}"
                                ,revSnap.data["Review"].toString(),revSnap.data["Rate"].toString().toInt())
                            reviewList.add(review)

                            if (reviewList.size==reviews.size()){
                                setUpRecyclerView(reviewList)
                            }
                        }
                    }
            }
        }


    }

    private fun setUpRecyclerView(reviews : ArrayList<ReviewData>){
        with(ProfileReviewsRecyclerView){
            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
            itemAnimator = DefaultItemAnimator()
            adapter = ReviewsRecViewAdapter(reviews)
        }
    }
}