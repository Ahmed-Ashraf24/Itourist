package com.example.itouristui.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.itouristui.UI.GeneralPage.ToursViewPagerFragments.AcceptedToursFragment
import com.example.itouristui.UI.GeneralPage.ToursViewPagerFragments.PendingOffersFragment
import com.example.itouristui.UI.GeneralPage.ToursViewPagerFragments.RateAndEndorsementFragment

class ToursViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> RateAndEndorsementFragment()

            1 -> PendingOffersFragment()

            2 -> AcceptedToursFragment()

            else -> RateAndEndorsementFragment()
        }
    }
}