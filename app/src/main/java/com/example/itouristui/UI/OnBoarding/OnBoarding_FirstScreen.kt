package com.example.itouristui.UI.OnBoarding

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.itouristui.R
import com.example.itouristui.UI.Authentication.LoginFragment
import me.relex.circleindicator.CircleIndicator3

class OnBoarding_FirstScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.onboarding_fragment_first_screen, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.onBoardingViewPagerID)
        val onBoardingNextButton = view.findViewById<Button>(R.id.OnBoardingNextButtonID1)
        val onBoardingSkipButton = view.findViewById<Button>(R.id.OnBoardingSkipButtonID1)

        onBoardingNextButton.setOnClickListener {
            viewPager?.currentItem = 1
        }

        onBoardingSkipButton.setOnClickListener {
            val loginFragment = LoginFragment()

            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.AuthenticationFragmentContainerView, loginFragment)
            transaction.addToBackStack(null)
            transaction.commit()

            onBoardingFinished()

            if (ContextCompat.checkSelfPermission(requireContext().applicationContext,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity() , arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),12312)
            }
        }

        val indicator = view.findViewById<CircleIndicator3>(R.id.OnBoardingCircleIndicator)

        indicator.setViewPager(viewPager)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}