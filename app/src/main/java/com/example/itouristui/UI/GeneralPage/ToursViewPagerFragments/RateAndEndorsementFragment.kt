package com.example.itouristui.UI.GeneralPage.ToursViewPagerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.EndorsementsAdapter
import com.example.itouristui.Adapters.ReviewsRecViewAdapter
import com.example.itouristui.R
import com.example.itouristui.models.ReviewData
import kotlinx.android.synthetic.main.fragment_rate_and_endorsement.endorsementsRecViewID
import kotlinx.android.synthetic.main.fragment_reviews.ProfileReviewsRecyclerView

class RateAndEndorsementFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rate_and_endorsement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(endorsementsRecViewID){
            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
            adapter = EndorsementsAdapter()
        }
    }
}