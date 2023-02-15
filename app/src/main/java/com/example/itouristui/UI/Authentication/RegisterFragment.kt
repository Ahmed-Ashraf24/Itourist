package com.example.itouristui.UI.Authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.itouristui.UI.Dialogs.DatePickerDialog
import com.example.itouristui.R
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container , false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val dateOfBirth = StringBuilder()
        SignUpBirthdayEditText.setOnClickListener {
            DatePickerDialog {
                SignUpBirthdayEditText.text =""
                dateOfBirth.append("${it[0]}/${it[1]}/${it[2]}")
                SignUpBirthdayEditText.text = dateOfBirth.toString()
            }.show(this.childFragmentManager ,"DatePickerTag")
        }

    }

}