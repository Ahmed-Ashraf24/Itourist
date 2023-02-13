package com.example.itouristui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ViewAnimator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private enum class LockState(val value : Int) {
        LOCK(R.drawable.ic_baseline_lock_24),
        UNLOCK(R.drawable.ic_baseline_lock_open_24)
    }

    private var lockIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showCustomUI()

        LogInButton.setOnClickListener{
            startActivity(Intent(this , MainActivity2::class.java))
        }

        RegisterMainButton.setOnClickListener {
            startActivity(Intent(this , RegisterActivity::class.java))
        }
        ForgotPassword.setOnClickListener {
            startActivity(Intent(this , VerificationActivity::class.java))

        }

    }


    private fun showCustomUI(){
        window.decorView.apply {
            systemUiVisibility =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    fun lock(view: View) {
        val picResID : Int = if (lockIndex==0){
            lockIndex=1
            LockState.UNLOCK.value
        } else {
            lockIndex=0
            LockState.LOCK.value
        }
        val lockAnimator : ObjectAnimator = ObjectAnimator.ofFloat( lockImage, ViewAnimator.ALPHA , 0.0f )
        lockAnimator.repeatCount = 1
        lockAnimator.repeatMode = ObjectAnimator.REVERSE
        lockAnimator.delayUntilDone(picResID)
        lockAnimator.start()
    }

    private fun ObjectAnimator.delayUntilDone( resID : Int){

        addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?) {
                lockImage.isEnabled = false
            }

            override fun onAnimationRepeat(animation: Animator?) {
                lockImage.setImageResource(resID)
            }

            override fun onAnimationEnd(animation: Animator?) {
                lockImage.isEnabled = true
            }
        })
    }
}