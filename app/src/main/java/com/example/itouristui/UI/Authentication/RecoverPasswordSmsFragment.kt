package com.example.itouristui.UI.Authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.itouristui.R
import com.example.itouristui.UI.Dialogs.VerificationCodeDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_recover_password_sms.*
import java.util.concurrent.TimeUnit


class RecoverPasswordSmsFragment(phone:String) : Fragment() {
val phone=phone

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recover_password_sms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth= FirebaseAuth.getInstance()
        val phoneNumber = phone
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    // OTP verification completed automatically
                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    showToast(exception.toString())
                    // OTP verification failed
                    // Handle the error
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {

                    ConfirmDateButton.setOnClickListener {
                  val credential = PhoneAuthProvider.getCredential( verificationId,sms_code_view.enteredCode)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnSuccessListener { Toast.makeText(
                            requireContext(),
                            "A link was sent to your email",
                            Toast.LENGTH_SHORT
                        ).show() }
                        .addOnFailureListener {
                            Toast.makeText(
                                requireContext(),
                                it.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext() , text , Toast.LENGTH_SHORT).show()
    }
}