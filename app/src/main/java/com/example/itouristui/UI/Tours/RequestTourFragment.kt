package com.example.itouristui.UI.Tours

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appyvet.materialrangebar.RangeBar
import com.bumptech.glide.Glide
import com.example.itouristui.R
import kotlinx.android.synthetic.main.fragment_request_tour.*

class RequestTourFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_request_tour,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle->
            FormCityNameEditText.setText(bundle.getString("CITY_NAME"))
            bundle.getString("CITY_IMAGE","").takeIf { it.isNotBlank() }?.let {
                    pic-> Glide.with(requireContext()).load(pic).into(FormCityImageView)
            }
        }

        FormBudgetRangeBar.setOnRangeBarChangeListener(object : RangeBar.OnRangeBarChangeListener {
            override fun onRangeChangeListener(rangeBar: RangeBar?, leftPinIndex: Int, rightPinIndex: Int, leftPinValue: String?, rightPinValue: String?) {
               FormRangeOfBudgetEditText.setText("$leftPinValue - $rightPinValue")
            }

            override fun onTouchStarted(rangeBar: RangeBar?) {}

            override fun onTouchEnded(rangeBar: RangeBar?) {  }
        })
    }
}