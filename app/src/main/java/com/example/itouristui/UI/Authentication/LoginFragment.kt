package com.example.itouristui.UI.Authentication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ViewAnimator
import com.example.itouristui.*
import com.example.itouristui.UI.GeneralPage.GeneralActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private enum class LockState(val value: Int) {
        LOCK(R.drawable.ic_baseline_lock_24),
        UNLOCK(R.drawable.ic_baseline_lock_open_24)
    }

    private var lockIndex = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LogInButton.setOnClickListener{
            startActivity(Intent(requireContext() , GeneralActivity::class.java))
        }


        CreateNewAccountButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.AuthenticationFragmentContainerView, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }

        ForgotPasswordButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.AuthenticationFragmentContainerView, VerificationFragment())
                .addToBackStack(null)
                .commit()
        }

        LoginHideShowPasswordButton.setOnClickListener {
            val picResID: Int = if (lockIndex == 0) {
                lockIndex = 1
                LockState.UNLOCK.value
            } else {
                lockIndex = 0
                LockState.LOCK.value
            }
            val lockAnimator: ObjectAnimator =
                ObjectAnimator.ofFloat(LoginHideShowPasswordButton, ViewAnimator.ALPHA, 0.0f)
            lockAnimator.repeatCount = 1
            lockAnimator.repeatMode = ObjectAnimator.REVERSE
            lockAnimator.delayUntilDone(picResID)
            lockAnimator.start()
        }

    }

    private fun ObjectAnimator.delayUntilDone(resID: Int) {

        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                LoginHideShowPasswordButton.isEnabled = false
            }

            override fun onAnimationRepeat(animation: Animator?) {
                LoginHideShowPasswordButton.setImageResource(resID)
            }

            override fun onAnimationEnd(animation: Animator?) {
                LoginHideShowPasswordButton.isEnabled = true
            }
        })
    }

}