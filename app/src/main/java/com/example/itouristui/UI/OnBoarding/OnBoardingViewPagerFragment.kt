package com.example.itouristui.UI.OnBoarding

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.itouristui.Adapters.OnBoardingViewPagerAdapter
import com.example.itouristui.R
import me.relex.circleindicator.CircleIndicator3

class OnBoardingViewPagerFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.onboarding_fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
            OnBoarding_FirstScreen(),
            OnBoarding_SecondScreen(),
            OnBoarding_ThirdScreen()
        )

        val adapter = OnBoardingViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val viewPager = view.findViewById<ViewPager2>(R.id.onBoardingViewPagerID)

        viewPager.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}