package com.example.itouristui.UI.GeneralPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itouristui.Adapters.ProfileViewPagerAdapter
import com.example.itouristui.Adapters.ToursViewPagerAdapter
import com.example.itouristui.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_profile.ProfilePictureImageView
import kotlinx.android.synthetic.main.fragment_profile.ProfileTabLayout
import kotlinx.android.synthetic.main.fragment_profile.ProfileViewPager
import kotlinx.android.synthetic.main.fragment_tours.ToursTabLayout
import kotlinx.android.synthetic.main.fragment_tours.ToursViewPager

class ToursFragment : Fragment() {

    var titles = arrayOf("Your rate","Pending offers","Accepted tours")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tours, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ToursViewPager.adapter = ToursViewPagerAdapter(requireActivity())

        let {
            TabLayoutMediator(ToursTabLayout, ToursViewPager){
                    tab, position ->
                tab.text = titles[position]
            }.attach()
        }
    }
}