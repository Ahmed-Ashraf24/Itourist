package com.example.itouristui.UI.DisplayMore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.Places_adapter
import com.example.itouristui.Adapters.RequestsCardsAdapter
import com.example.itouristui.R
import com.example.itouristui.models.PlaceImportantData
import kotlinx.android.synthetic.main.fragment_liked_cities.ProfileLikedCitiesRecyclerView
import kotlinx.android.synthetic.main.fragment_requests.RequestsCardsRecViewID

class RequestsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(RequestsCardsRecViewID){
            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
                adapter = RequestsCardsAdapter()
        }
    }

}