package com.example.itouristui

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(RVsuggestedPlacesId){
            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)
        }

        with(RVpopularPlacesId){
            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)
            adapter = horizontal_recyclerview_adapter()
        }

        with(RVnearbyPlacesId){
            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)
            adapter = horizontal_recyclerview_adapter()
        }

        val ss: SpannableString = SpannableString("See More")
        val underLineSpan = UnderlineSpan()

        ss.setSpan(underLineSpan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tVId5.text = ss
        tVId7.text = ss
        tVId9.text = ss
    }
}