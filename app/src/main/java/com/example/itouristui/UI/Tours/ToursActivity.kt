package com.example.itouristui.UI.Tours

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.itouristui.R

class ToursActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tours)

        val selectedFragmentBundle = Bundle()
        val selectedToursFragment = when(intent.getStringExtra("SELECTED_TOURS_FRAGMENT")){
            "TOURS_REQUEST_FORM"->{
                with(intent){
                    selectedFragmentBundle.putString("CITY_NAME",getStringExtra("CITY_NAME"))
                    selectedFragmentBundle.putString("CITY_IMAGE",getStringExtra("CITY_IMAGE"))
                }
                RequestTourFragment().apply {
                    arguments = selectedFragmentBundle
                }
            }

            "CITY_TOURS"->{
                with(intent){
                    selectedFragmentBundle.putString("CITY_NAME",getStringExtra("CITY_NAME"))
                }
                CityRequestsFragment().apply {
                    arguments = selectedFragmentBundle
                }
            }

            else -> RequestTourFragment()
        }

        supportFragmentManager.beginTransaction().add(R.id.ToursFragmentContainerView,selectedToursFragment).commit()

    }


}