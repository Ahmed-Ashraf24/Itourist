package com.example.itouristui.UI.DisplayMore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.itouristui.R

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        showCustomUI()

        val selectedFragmentBundle = Bundle()
        val selectedDisplayFragment = when(intent.getStringExtra("SELECTED_DISPLAY_FRAGMENT")){
            "PLACE_INFO"-> {
                with(intent){
                    selectedFragmentBundle.putParcelable("IMPORTANT_PLACE",getParcelableExtra("IMPORTANT_PLACE"))
                }
                PlaceInfoFragment().apply {
                    arguments = selectedFragmentBundle
                }
            }

            "PLACES_LIST"-> {
                with(intent){
                    selectedFragmentBundle.putString("SELECTED_CATEGORY",getStringExtra("SELECTED_CATEGORY"))
                    selectedFragmentBundle.putDouble("LAT",getDoubleExtra("LAT",0.0))
                    selectedFragmentBundle.putDouble("LON",getDoubleExtra("LON",0.0))
                }
                PlacesListFragment().apply {
                    arguments = selectedFragmentBundle
                }
            }

            else-> PlacesListFragment()
        }
        supportFragmentManager.beginTransaction().add(R.id.DisplayFragmentContainerView,selectedDisplayFragment).commit()
    }

    private fun showCustomUI(){
        window.decorView.apply {
            systemUiVisibility =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }
}

