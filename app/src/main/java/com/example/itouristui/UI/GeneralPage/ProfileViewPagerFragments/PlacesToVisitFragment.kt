package com.example.itouristui.UI.GeneralPage.ProfileViewPagerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.Places_adapter
import com.example.itouristui.R
import com.example.itouristui.models.PlaceImportantData
import kotlinx.android.synthetic.main.fragment_places_to_visit.*


class PlacesToVisitFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places_to_visit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val places=ArrayList<PlaceImportantData>()
        val place = PlaceImportantData("pizza pino","23 July St.,PortSaid,Egypt","5 Km" ,0.0,0.0)
        for (i in 1..10){
            places.add(place)
        }

        with(ProfilePlacesToVisitRecyclerView){
            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
            adapter = Places_adapter(places)
        }
    }

}