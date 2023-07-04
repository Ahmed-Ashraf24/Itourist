package com.example.itouristui.UI.Tours

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.appyvet.materialrangebar.RangeBar
import com.bumptech.glide.Glide
import com.example.itouristui.R
import com.example.itouristui.UI.Dialogs.DatePickerDialog
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
               RangeOfBudgetEditText.setText("$leftPinValue - $rightPinValue")
            }

            override fun onTouchStarted(rangeBar: RangeBar?) {}

            override fun onTouchEnded(rangeBar: RangeBar?) {  }
        })

        MaleCardView.setOnClickListener { AccompaniedByEditText.setText("Male") }
        FemaleCardView.setOnClickListener { AccompaniedByEditText.setText("Female") }

        CarPreferredCardView.setOnClickListener { CarPreferenceEditText.setText("Yes") }
        CarNotPreferredCardView.setOnClickListener { CarPreferenceEditText.setText("No") }

        AddDateOfArrivalImageView.setOnClickListener{
            DatePickerDialog{
                DateOfArrivalEditText.setText("${it[0]}/${it[1]}/${it[2]}")
            }.show(childFragmentManager,"DIALOG_OF_REQUEST_DATE")
        }

        SubmitRequestButton.setOnClickListener {
            if (validatePrimaryFields()){

            }else Toast.makeText(requireContext(),"All Primary Fields Must Be Filled",Toast.LENGTH_LONG).show()
        }
    }

    private fun validatePrimaryFields() :Boolean{
        return (IndividualCountEditText.text?:"").toString().isNotBlank()
                &&(DateOfArrivalEditText.text?:"").toString().isNotBlank()
                &&(RangeOfBudgetEditText.text?:"").toString().isNotBlank()
                &&(DescriptionEditText.text?:"").toString().isNotBlank()
    }
}