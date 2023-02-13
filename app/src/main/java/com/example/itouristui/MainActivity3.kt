package com.example.itouristui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main3.*
import kotlinx.android.synthetic.main.fragment_profile_v2.*

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        supportFragmentManager.beginTransaction().add(R.id.FragmentContainerProfile , BioFragment()).commit()

        CustomProfileNavBar.setItemSelected(R.id.navBioButtonId)
        CustomProfileNavBar.setOnItemSelectedListener { selectedNavButtonID->

            var selectedFragment : Fragment? = null
            when(selectedNavButtonID){
                R.id.navBioButtonId -> selectedFragment = BioFragment()

                R.id.navReviewsButtonId -> selectedFragment = ReviewsFragment()

                R.id.navPlacesToVisitButtonId -> selectedFragment = PlacesToVisitFragment()
            }
            selectedFragment?.let {
                supportFragmentManager.beginTransaction().replace(R.id.FragmentContainerMain ,
                    selectedFragment!!
                ).commit()
            }

        }
    }
}