package com.example.itouristui.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.itouristui.UI.GeneralPage.ProfileViewPagerFragments.GuideToursFragment
import com.example.itouristui.UI.GeneralPage.ProfileViewPagerFragments.PersonalFragment
import com.example.itouristui.UI.GeneralPage.ProfileViewPagerFragments.LikedCitiesFragment
import com.example.itouristui.UI.GeneralPage.ProfileViewPagerFragments.ReviewsFragment
import com.example.itouristui.UI.GeneralPage.ProfileViewPagerFragments.UserToursFragment

class ProfileViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> PersonalFragment()

            1 -> ReviewsFragment()

            2 -> LikedCitiesFragment()

            3 -> GuideToursFragment()

            else -> PersonalFragment()
        }
    }
}