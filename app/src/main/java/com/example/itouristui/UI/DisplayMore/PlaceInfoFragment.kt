package com.example.itouristui.UI.DisplayMore

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.ReviewsRecViewAdapter
import com.example.itouristui.R
import com.example.itouristui.models.PlaceImportantData
import kotlinx.android.synthetic.main.fragment_item.*

class PlaceInfoFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val placeData = it.getParcelable<PlaceImportantData>("IMPORTANT_PLACE").also {
                PlaceInfoFragmentNameTV.text = it!!.nameOfPlace
                PlaceInfoFragmentAddressTV.text = it.address
                PlaceInfoFragmentDistanceTV.text = it.distanceAway
            }
            PlaceInfoFragmentOnMapButton.setOnClickListener {
                val mapUriNavigation = Uri.parse("geo:${placeData!!.lat}, ${placeData.lon}")
                Intent(Intent.ACTION_VIEW,mapUriNavigation).apply {
                    setPackage("com.google.android.apps.maps")
                }.also { intent->
                    startActivity(intent)
                }
            }

            PlaceInfoFragmentRouteButton.setOnClickListener {
                val mapUriNavigation = Uri.parse("google.navigation:q=${placeData!!.lat},${placeData.lon}")
                Intent(Intent.ACTION_VIEW,mapUriNavigation).apply {
                    setPackage("com.google.android.apps.maps")
                }.also { intent->
                    startActivity(intent)
                }
            }

            val ss = SpannableString(OverallRateTextView.text)

            ss.setSpan(RelativeSizeSpan(2f),0,1,0)
            OverallRateTextView.text = ss

        }

        with(ReviewsRecyclerView){
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
            adapter = ReviewsRecViewAdapter()
        }





    }


}