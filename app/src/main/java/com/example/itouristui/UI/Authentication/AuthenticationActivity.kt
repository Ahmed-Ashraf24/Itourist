package com.example.itouristui.UI.Authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.itouristui.R

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        showCustomUI()

        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction().add(R.id.AuthenticationFragmentContainerView , loginFragment).commit()

    }

    private fun showCustomUI(){
        window.decorView.apply {
            systemUiVisibility =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

}