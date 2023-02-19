package com.example.itouristui.UI.Authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.itouristui.FirebaseObj
import com.example.itouristui.R
import kotlinx.android.synthetic.main.fragment_recovery_options.*


class RecoveryOptionsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recovery_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RecoverByEmailOptionLayout.setOnClickListener {
            /*TODO MAKE SURE EMAIL EXIST BEFORE PROCEEDING*/
            arguments?.let {
                FirebaseObj.auth.sendPasswordResetEmail(it.getString("EmailPasswordReset")!!)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "A link was sent to your email",Toast.LENGTH_SHORT).show()
                        parentFragmentManager.popBackStackImmediate()
                    }
            }?: Toast.makeText(requireContext(),"Insert your email again",Toast.LENGTH_LONG).show()

        }

        RecoverBySMSOptionLayout.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(R.id.AuthenticationFragmentContainerView,RecoverPasswordSmsFragment())
                .addToBackStack(null)
                .commit()
        }

    }

}