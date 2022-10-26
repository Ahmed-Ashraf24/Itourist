package com.example.itouristui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.it.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register)
        showCustomUI()

        val dateOfBirth = StringBuilder()
        BirthdayRegisterEditText.setOnClickListener {
            DatePickerDialog {
                BirthdayRegisterEditText.text =""
                dateOfBirth.append("${it[0]}/${it[1]}/${it[2]}")
                BirthdayRegisterEditText.text = dateOfBirth.toString()
            }.show(supportFragmentManager ,"DatePickerTag")
        }
    }

    private fun showCustomUI(){
        window.decorView.apply {
            systemUiVisibility =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }
}