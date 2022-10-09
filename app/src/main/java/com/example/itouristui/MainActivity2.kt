package com.example.itouristui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var checkedRadioButton = RadioGroupMainOptionsView.checkedRadioButtonId
        RadioGroupMainOptionsView.setOnCheckedChangeListener { radioGroup, i ->
            val wasChecked = findViewById<RadioButton>(checkedRadioButton)
            wasChecked.text = ""
            checkedRadioButton = radioGroup.checkedRadioButtonId
            when(i){
                HomeRadioButton.id->HomeRadioButton.text = "Home"
                SearchRadioButton.id->SearchRadioButton.text = "Search"
                ProfileRadioButton.id->ProfileRadioButton.text = "Profile"
                ChatRadioButton.id->ChatRadioButton.text = "Chat"
            }

        }
    }
}