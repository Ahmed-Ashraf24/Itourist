package com.example.itouristui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        MainAct3NavView.setNavigationItemSelectedListener(this)
        MainAct3NavView.itemIconTintList = null

        var checkedRadioButton = RadioGroupMainOptionsView.checkedRadioButtonId
        RadioGroupMainOptionsView.setOnCheckedChangeListener { radioGroup, i ->
            val wasChecked = findViewById<RadioButton>(checkedRadioButton)
            wasChecked.text = ""
            checkedRadioButton = radioGroup.checkedRadioButtonId
            when(i){
                HomeRadioButton.id->HomeRadioButton.text = "Home"
                SearchRadioButton.id->{
                    SearchRadioButton.text = "Search"
                    startActivity(Intent(this , MainActivity3::class.java))
                }
                ProfileRadioButton.id->ProfileRadioButton.text = "Profile"
                ChatRadioButton.id->ChatRadioButton.text = "Chat"
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

        }
        return true
    }
}