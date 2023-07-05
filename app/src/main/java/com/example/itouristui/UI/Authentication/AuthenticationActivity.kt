package com.example.itouristui.UI.Authentication

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.example.itouristui.Data.Remote.CityCountryApiObject
import com.example.itouristui.Data.Remote.PicturesAPI
import com.example.itouristui.R
import com.example.itouristui.UI.OnBoarding.OnBoardingViewPagerFragment
import com.example.itouristui.UI.OnBoarding.OnBoarding_FirstScreen
import com.example.itouristui.Utilities.CustomRetrofitCallBack
import com.example.itouristui.models.CityDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        showCustomUI()

        PicturesAPI.pictureApiInterface.getCities("oldies restaurant portsaid").enqueue(CustomRetrofitCallBack<String>{
            println(it.body())
        })


        if (onBoardingFinished()){
            val loginFragment = LoginFragment()
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.AuthenticationFragmentContainerView, loginFragment)
            transaction.addToBackStack(null)
            transaction.commit()

            if (ContextCompat.checkSelfPermission(applicationContext,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this , arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),12312)
            }
        }else{
            val onBoardingFragments = OnBoardingViewPagerFragment()
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.AuthenticationFragmentContainerView, onBoardingFragments)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun showCustomUI(){
        window.decorView.apply {
            systemUiVisibility =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}