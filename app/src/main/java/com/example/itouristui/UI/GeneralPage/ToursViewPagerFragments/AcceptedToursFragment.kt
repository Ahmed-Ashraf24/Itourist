package com.example.itouristui.UI.GeneralPage.ToursViewPagerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.PendingOffersAdapter
import com.example.itouristui.Adapters.RequestsCardsAdapter
import com.example.itouristui.R
import kotlinx.android.synthetic.main.fragment_accepted_tours.acceptedOffersRecViewID
import kotlinx.android.synthetic.main.fragment_pending_offers.pendingOffersRecViewID

class AcceptedToursFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accepted_tours, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(acceptedOffersRecViewID){
            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
            adapter = RequestsCardsAdapter(emptyList(), emptyList()){}
        }
    }
}