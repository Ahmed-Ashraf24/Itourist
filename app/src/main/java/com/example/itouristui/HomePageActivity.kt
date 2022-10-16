package com.example.itouristui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.RadioButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePageActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: horizontal_recyclerview_adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        showCustomUi()

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

        recyclerView = findViewById(R.id.RVsuggestedPlacesId)
        adapter = horizontal_recyclerview_adapter()

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        var text: String = "See more"
        var ss: SpannableString = SpannableString(text)
        var UnderlineSpan: UnderlineSpan = UnderlineSpan()

        ss.setSpan(UnderlineSpan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tVId5.text = ss
    }

    private fun showCustomUi(){
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }
}