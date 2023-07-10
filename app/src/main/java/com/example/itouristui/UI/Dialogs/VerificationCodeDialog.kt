package com.example.itouristui.UI.Dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.itouristui.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_recover_password_sms.*


class VerificationCodeDialog(verficationid:String) : DialogFragment() {
    val verificationId=verficationid
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recover_password_sms, container, false)
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("before clicking : "+verificationId)
        ConfirmDateButton.setOnClickListener {
            println("before clicking : "+verificationId)

            val credential = PhoneAuthProvider.getCredential(verificationId!!,sms_code_view.enteredCode)
        FirebaseAuth.getInstance().currentUser?.linkWithCredential(credential)!!.addOnSuccessListener {
            showToast("added sucessfully")
    }
        }
}
    private fun showToast(text: String) {
        Toast.makeText(requireContext() , text , Toast.LENGTH_SHORT).show()
    }
}