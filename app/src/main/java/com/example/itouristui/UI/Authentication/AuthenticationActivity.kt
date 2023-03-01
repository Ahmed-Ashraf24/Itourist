package com.example.itouristui.UI.Authentication

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.itouristui.Data.Remote.CityCountryApiObject
import com.example.itouristui.Data.Remote.PicturesAPI
import com.example.itouristui.R
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

        if (ContextCompat.checkSelfPermission(applicationContext,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this , arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),12312)
        }

        PicturesAPI.pictureApiInterface.getCities("oldies restaurant portsaid").enqueue(CustomRetrofitCallBack<String>{
            println(it.body())
        })




        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction().add(R.id.AuthenticationFragmentContainerView , loginFragment).commit()

    }

    private fun showCustomUI(){
        window.decorView.apply {
            systemUiVisibility =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

}