package com.example.itouristui.UI.GeneralPage.ProfileViewPagerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itouristui.FirebaseObj
import com.example.itouristui.R
import com.example.itouristui.models.UserPlainData
import kotlinx.android.synthetic.main.fragment_personal.*

class PersonalFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseObj.fireStore.collection("Users").document(FirebaseObj.uid).get().addOnSuccessListener {
            val user = it.toObject(UserPlainData::class.java)
            ProfileEmailID.text = user!!.email
            ProfilePhoneID.text = user.phoneNumber
            ProfileDOBID.text = user.dataOfBirth
        }
    }
}