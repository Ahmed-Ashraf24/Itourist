package com.example.itouristui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.fragment_profile_v2.*

class MainActivity2 : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        MainAct3NavView.setNavigationItemSelectedListener(this)
        MainAct3NavView.itemIconTintList = null
        showCustomUI()

        supportFragmentManager.beginTransaction().add(R.id.FragmentContainerMain , HomeFragment()).commit()

        CustomBottomNavBar.setItemSelected(R.id.navHomeButtonId)
        CustomBottomNavBar.setOnItemSelectedListener { selectedNavButtonID->

            var selectedFragment : Fragment? = null
           when(selectedNavButtonID){
               R.id.navHomeButtonId -> selectedFragment = HomeFragment()

               R.id.navSearchButtonId -> selectedFragment = SearchFragment()

               R.id.navProfileButtonId -> selectedFragment = ProfileFragment()
           }
            selectedFragment?.let {
                supportFragmentManager.beginTransaction().replace(R.id.FragmentContainerMain ,
                    selectedFragment!!
                ).commit()
            }

        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

        }
        return true
    }

    private fun showCustomUI(){
        window.decorView.apply {
            systemUiVisibility =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }
}