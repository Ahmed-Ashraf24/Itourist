package com.example.itouristui.UI.Authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.itouristui.FirebaseObj
import com.example.itouristui.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_recovery_options.*


class RecoveryOptionsFragment(email:String) : Fragment() {
val email=email

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
            FirebaseObj.auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    Toast.makeText(
                        requireContext(),
                        "A link was sent to your email",
                        Toast.LENGTH_SHORT
                    ).show()
                    parentFragmentManager.popBackStackImmediate()
                    /*TODO MAKE SURE EMAIL EXIST BEFORE PROCEEDING*/
//            arguments?.let {
//                FirebaseObj.auth.sendPasswordResetEmail(email)
//                    .addOnSuccessListener {
//                        Toast.makeText(requireContext(), "A link was sent to your email",Toast.LENGTH_SHORT).show()
//                        parentFragmentManager.popBackStackImmediate()
//                    }
//            }?: Toast.makeText(requireContext(),"Insert your email again",Toast.LENGTH_LONG).show()

                }

            RecoverBySMSOptionLayout.setOnClickListener {
                val firestoreref = FirebaseFirestore.getInstance()
                val query = firestoreref.collection("Users")
                query.get()
                    .addOnSuccessListener { querySnapshot ->

                        for (document in querySnapshot) {

                            document.let {
                                if (document.exists()) {
                                    if (it.getString("email") == email) {
                                        val email2 = it.getString("email")
                                        val phone = it.getString("phoneNumber")
                                        parentFragmentManager.beginTransaction()
                                            .replace(
                                                R.id.AuthenticationFragmentContainerView,
                                                RecoverPasswordSmsFragment(phone!!)
                                            )
                                            .addToBackStack(null)
                                            .commit()
                                        println("Email: " + email2 + "phone number + " + phone)

                                    }

                                    println("Email: ${document.getString("email")}")
                                }
                            }
                        }

                    }
                    .addOnFailureListener { exception ->
                        // Handle any errors
                        println("Error getting documents: $exception")
                    }

            }

        }

    }
}