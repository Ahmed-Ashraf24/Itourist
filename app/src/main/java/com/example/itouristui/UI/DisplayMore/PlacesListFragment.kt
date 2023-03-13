package com.example.itouristui.UI.DisplayMore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.CategorySearchResultRecViewAdapter

import com.example.itouristui.R
import com.example.itouristui.Utilities.CustomTomtomCallback
import com.example.itouristui.models.PlaceImportantData
import com.example.itouristui.models.places
import com.tomtom.sdk.common.location.GeoPoint
import com.tomtom.sdk.search.SearchOptions
import com.tomtom.sdk.search.model.geometry.CircleGeometry
import com.tomtom.sdk.search.model.result.SearchResult
import com.tomtom.sdk.search.online.OnlineSearch
import kotlinx.android.synthetic.main.activity_display.*
import kotlinx.android.synthetic.main.fragment_places_list.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min

class PlacesListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_places_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {args->
            val (currentLat,currentLon) =  Pair(args.getDouble("LAT",0.0),args.getDouble("LON",0.0))
            val query = args.getString("SELECTED_CATEGORY").also {
                FragmentListCategoryNameTV.text = it!!.substring(0, min(17,it.length))
            }

            val search= OnlineSearch.create(requireContext(),"iKKlcatVgAGyYIADEYdmhjYFE6DanMP5")
            val circleGeometry = CircleGeometry(GeoPoint(currentLat,currentLon) ,1000)
            val lang = Locale("ar")

            val searchOptions = SearchOptions(query = query.toString(), searchAreas = setOf(circleGeometry), limit = 20, locale = lang)
            search.search(searchOptions,CustomTomtomCallback{result->

                recyclerView1.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                recyclerView1.adapter = CategorySearchResultRecViewAdapter(result.results){
                    getImportantPlaceData(it)
                }
            })

        }
    }

    private fun getImportantPlaceData(searchResult : SearchResult){
        PlaceImportantData(searchResult.poi?.names?.first()?:"UnKnown Place Name",
            searchResult.address?.run { "$streetName, $localName, $country" }?:"Unknown Address",
            searchResult.distance?.inKilometers().toString().dropLast(5)+" Km",
            searchResult.position.latitude,
            searchResult.position.longitude).also { impPlaceData->
            displayPlaceInfo(impPlaceData)
        }
    }

    private fun displayPlaceInfo(placeImpData : PlaceImportantData){
        Bundle().apply {
            putString("SELECTED_DISPLAY_FRAGMENT","PLACE_INFO")
            putParcelable("IMPORTANT_PLACE",placeImpData)
        }.also {
            val placeInfoFragment = PlaceInfoFragment().apply {
                arguments = it
            }
            parentFragmentManager.beginTransaction().replace(R.id.DisplayFragmentContainerView,placeInfoFragment)
                .addToBackStack(null).commit()
        }
    }

}