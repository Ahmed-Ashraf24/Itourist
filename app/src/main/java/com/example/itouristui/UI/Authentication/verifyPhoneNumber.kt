package com.example.itouristui.UI.Authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.itouristui.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_recover_password_sms.*




class verifyPhoneNumber : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recover_password_sms, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var verificationId: String? = null
        arguments?.let{
             verificationId=it.getString("VERIFICATION_ID")
            println("verfigcawweweqa " +verificationId)}
        ConfirmDateButton.setOnClickListener {


            }


        }

    }


