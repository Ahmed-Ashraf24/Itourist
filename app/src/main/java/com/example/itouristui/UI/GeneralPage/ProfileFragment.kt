package com.example.itouristui.UI.GeneralPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itouristui.Adapters.ProfileViewPagerAdapter
import com.example.itouristui.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    var titles = arrayOf("Bio","Reviews","Places to visit")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ProfileViewPager.adapter = ProfileViewPagerAdapter(requireActivity())

        let {
            TabLayoutMediator(ProfileTabLayout, ProfileViewPager){
                tab, position ->
                    tab.text = titles[position]
            }.attach()
        }
    }

}