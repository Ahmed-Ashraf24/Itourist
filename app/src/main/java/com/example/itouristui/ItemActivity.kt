package com.example.itouristui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import kotlinx.android.synthetic.main.fragment_item.*

class ItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_item)

        showCustomUI()
        val rateFromView = OverallRateTextView.text.toString()
        OverallRateTextView.text = SpannableString(rateFromView).apply {
            setSpan(RelativeSizeSpan(2f) , 0,1,0)
        }
    }

    private fun showCustomUI(){
        window.decorView.apply {
            systemUiVisibility =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }
}