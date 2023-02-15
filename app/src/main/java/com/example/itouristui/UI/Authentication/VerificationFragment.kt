package com.example.itouristui.UI.Authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.itouristui.R
import com.example.itouristui.UI.Dialogs.VerificationCodeDialog
import kotlinx.android.synthetic.main.fragment_verification.*

class VerificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_verification, container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        VerificationCodeEditText.setOnClickListener{
            val dialog= VerificationCodeDialog()
            dialog.show(this.childFragmentManager,"verificationDialog")
        }

    }
}