package com.example.itouristui.Utilities

import android.text.Editable
import android.text.TextWatcher

class CustomTextWatcher(val lambdaBody : (String)->(Unit)) : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        lambdaBody(p0.toString())
    }

    override fun afterTextChanged(p0: Editable?) {
    }
}