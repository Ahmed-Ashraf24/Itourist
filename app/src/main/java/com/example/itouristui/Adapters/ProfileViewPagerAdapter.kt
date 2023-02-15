package com.example.itouristui.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.itouristui.UI.GeneralPage.ProfileViewPagerFragments.BioFragment
import com.example.itouristui.UI.GeneralPage.ProfileViewPagerFragments.PlacesToVisitFragment
import com.example.itouristui.UI.GeneralPage.ProfileViewPagerFragments.ReviewsFragment

class ProfileViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> BioFragment()

            1 -> ReviewsFragment()

            2 -> PlacesToVisitFragment()

            else -> BioFragment()
        }
    }
}