package com.example.itouristui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ViewAnimator
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        showCustomUI()

        val backgroundAnimator : ObjectAnimator = ObjectAnimator.ofFloat(SplashScreenBG,ViewAnimator.ALPHA,1.0f).apply {
            duration = 700
        }

        val logoAlphaAnimator : ObjectAnimator = ObjectAnimator.ofFloat(sublogo_splashscreen , ViewAnimator.ALPHA , 1.0f).apply {
            duration = 700
            startDelay = 500
        }
        val subLogoTransAnimator : ObjectAnimator = ObjectAnimator.ofFloat(sublogo_splashscreen , ViewAnimator.TRANSLATION_X , 0.0f).apply {
            duration = 700
        }

        logoAlphaAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                backgroundAnimator.start()
                subLogoTransAnimator.start()
            }
        })
        val titleAlphaAnimator : ObjectAnimator = ObjectAnimator.ofFloat(title_splashscreen , ViewAnimator.ALPHA , 1.0f).apply {
            duration = 1700
        }
        val subTitleAlphasAnimator : ObjectAnimator = ObjectAnimator.ofFloat(subtitle_splashscreen , ViewAnimator.ALPHA , 1.0f).apply {
            duration = 1700
        }

        subLogoTransAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                title_splashscreen.visibility = View.VISIBLE
                subtitle_splashscreen.visibility = View.VISIBLE
                titleAlphaAnimator.start()
                subTitleAlphasAnimator.start()

            }
        })


        subTitleAlphasAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                Handler().postDelayed({
                    val intent = Intent(this@SplashScreenActivity , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                },1500)
            }
        })


        /**
         * Start the Animation Process
        * */
        logoAlphaAnimator.start()

    }

    private fun showCustomUI(){
        window.decorView.apply {
            systemUiVisibility =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }
}