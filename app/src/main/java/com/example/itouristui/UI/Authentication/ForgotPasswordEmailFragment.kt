package com.example.itouristui.UI.Authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itouristui.R
import kotlinx.android.synthetic.main.fragment_forgot_password_email.*


class ForgotPasswordEmailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigateToRecoveryOptionsButton.setOnClickListener{
            parentFragmentManager.beginTransaction().
            replace(R.id.AuthenticationFragmentContainerView , RecoveryOptionsFragment()).addToBackStack(null)
                .commit()
        }

    }


}