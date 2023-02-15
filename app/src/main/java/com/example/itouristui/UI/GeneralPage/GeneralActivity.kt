package com.example.itouristui.UI.GeneralPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.example.itouristui.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_general.*

class GeneralActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
        MainAct3NavView.setNavigationItemSelectedListener(this)
        MainAct3NavView.itemIconTintList = null
        showCustomUI()

        supportFragmentManager.beginTransaction().add(R.id.GeneralFragmentContainerView, HomeFragment()).commit()


        CustomBottomNavBar.setItemSelected(R.id.navHomeButtonId)
        CustomBottomNavBar.setOnItemSelectedListener { selectedNavButtonID->

           when(selectedNavButtonID){
               R.id.navHomeButtonId ->  HomeFragment()

               R.id.navSearchButtonId -> SearchFragment()

               R.id.navProfileButtonId -> ProfileFragment()
               else -> HomeFragment()
           }.also{
                supportFragmentManager.beginTransaction().replace(R.id.GeneralFragmentContainerView, it).commit()
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