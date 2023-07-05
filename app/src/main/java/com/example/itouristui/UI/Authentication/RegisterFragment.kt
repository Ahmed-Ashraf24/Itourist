package com.example.itouristui.UI.Authentication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.itouristui.FirebaseObj
import com.example.itouristui.UI.Dialogs.DatePickerDialog
import com.example.itouristui.R
import com.example.itouristui.UI.GeneralPage.GeneralActivity
import com.example.itouristui.Utilities.CustomTextWatcher
import com.example.itouristui.models.UserPlainData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {

    private var hasDigit = false
    private var hasCap = false
    private var hasSmall = false
    private var has8Char = false

    private val dataBase: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container , false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SignUpPasswordEditText.addTextChangedListener(passwordWatcher)
        SignUpCCP.registerCarrierNumberEditText(SignUpPhoneEditText)

        val dateOfBirth = StringBuilder()
        SignUpButton.setOnClickListener {
            when(true){
                hasNoEmptyFields().not()-> showToast("Some Fields are missing")
                arePasswordsIdentical().not()-> showToast("Passwords doesn't match")
                isPasswordValid().not() -> showToast("Password doesn't match the rule")
                SignUpCCP.isValidFullNumber.not()-> showToast("Phone number is not valid!")
                else -> {
                    SignUpButton.isEnabled = false

                    FirebaseObj.auth.createUserWithEmailAndPassword(
                        SignUpEmailEditText.text.toString().trim(),SignUpPasswordEditText.text.toString().trim())
                        .addOnSuccessListener {
                            val newUserPlainData = UserPlainData(it.user!!.uid,
                                SignUpFullNameEditText.text.toString().trim(),
                                SignUpEmailEditText.text.toString().trim(),
                                SignUpCCP.fullNumberWithPlus,
                                SignUpBirthdayEditText.text.toString(),
                                "","","",""
                            )
                            SignUpButton.isEnabled = true
                            savePerson(newUserPlainData)
                        }.addOnFailureListener {
                            SignUpButton.isEnabled = true
                            Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                        }

                }
            }

        }

        SignUpBirthdayEditText.setOnClickListener {
            DatePickerDialog {
                SignUpBirthdayEditText.text =""
                dateOfBirth.append("${it[0]}/${it[1]}/${it[2]}")
                SignUpBirthdayEditText.text = dateOfBirth.toString()
            }.show(this.childFragmentManager ,"DatePickerTag")
        }

    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext() , text , Toast.LENGTH_SHORT).show()
    }

    private fun hasNoEmptyFields() : Boolean{
        return SignUpFullNameEditText.text.toString().isNotBlank()&&
                SignUpEmailEditText.text.toString().isNotBlank()&&
                SignUpBirthdayEditText.text.toString().isNotBlank()&&
                SignUpPasswordEditText.text.toString().isNotBlank()&&
                SignUpConfirmPasswordEditText.text.toString().isNotBlank()
    }

    private fun arePasswordsIdentical():Boolean{
        return SignUpPasswordEditText.text.toString()==SignUpConfirmPasswordEditText.text.toString()
    }

    private fun isPasswordValid():Boolean{
        return hasSmall&&hasCap&&hasDigit&&has8Char
    }

    private val passwordWatcher = CustomTextWatcher{ input->

        has8Char = (input.length>=8)
        hasDigit = input.any { it.isDigit() }
        hasCap = input.any { it.isUpperCase() }
        hasSmall = input.any { it.isLowerCase() }

        (if (has8Char) "#A5D6A7" else "#D3D3D3").also { SignUp8Char.compoundDrawables[0].setTint(Color.parseColor(it)) }
        (if (hasDigit) "#A5D6A7" else "#D3D3D3").also { SignUp1Digit.compoundDrawables[0].setTint(Color.parseColor(it)) }
        (if (hasCap) "#A5D6A7" else "#D3D3D3").also { SignUp1Cap.compoundDrawables[0].setTint(Color.parseColor(it)) }
        (if (hasSmall) "#A5D6A7" else "#D3D3D3").also { SignUp1Small.compoundDrawables[0].setTint(Color.parseColor(it)) }
    }

    private fun savePerson(userPlainData : UserPlainData){
        dataBase.collection("Users").document(userPlainData.uid!!).set(userPlainData)
            .addOnSuccessListener {
                Toast.makeText(requireActivity(), "Registration done", Toast.LENGTH_SHORT).show()
            }
    }

}