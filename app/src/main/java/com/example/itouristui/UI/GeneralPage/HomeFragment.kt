package com.example.itouristui.UI.GeneralPage


import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.GeneralPageRecViewAdapter
import com.example.itouristui.R
import com.example.itouristui.UI.DisplayMore.DisplayActivity
import com.example.itouristui.Utilities.CustomTomtomCallback
import com.example.itouristui.models.PlaceImportantData
import com.tomtom.sdk.common.location.GeoPoint
import com.tomtom.sdk.search.SearchOptions
import com.tomtom.sdk.search.model.geometry.CircleGeometry
import com.tomtom.sdk.search.model.result.SearchResult
import com.tomtom.sdk.search.online.OnlineSearch
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment(){

    var wasPreviouslyCreated = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NearbyPlacesRecyclerView.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)
        PopularPlacesRecyclerView.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)
        SuggestedPlacesRecyclerView.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)


        val ss: SpannableString = SpannableString("See More")
        val underLineSpan = UnderlineSpan()

        ss.setSpan(underLineSpan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tVId5.text = ss


        arguments?.let {args->

            wasPreviouslyCreated=true
            val currentPlace = args.getString("CURRENT_LOCATION")
            val currentLat = args.getDouble("LAT")
            val currentLon = args.getDouble("LON")

            CurrentLocationTextView.text = currentPlace

            val search= OnlineSearch.create(requireContext(),"iKKlcatVgAGyYIADEYdmhjYFE6DanMP5")
            val circleGeometry = CircleGeometry(GeoPoint(currentLat,currentLon) ,500)
            val lang = Locale("ar")

            val searchOptionsNearby = SearchOptions(query = "store",searchAreas = setOf(circleGeometry), limit = 20)
            search.search(searchOptionsNearby, CustomTomtomCallback{ results->
                NearbyPlacesRecyclerView.adapter = GeneralPageRecViewAdapter(results.results){
                    getImportantPlaceData(it)
                }
                HomeFragmentShimmer.apply {
                    stopShimmerAnimation()
                    visibility = View.GONE
                }

                HomeFragmentNestedLayoutContainer.visibility = View.VISIBLE
            })

            val searchOptionsPopular = SearchOptions(query = "Tourist Attraction",searchAreas = setOf(circleGeometry), limit = 20, locale = lang)
            search.search(searchOptionsPopular, CustomTomtomCallback{ results->
                println(results.results.first().toString())
                PopularPlacesRecyclerView.adapter = GeneralPageRecViewAdapter(results.results){
                    getImportantPlaceData(it)
                }

            })

        }?:Toast.makeText(requireContext(),"A Problem has occurred,You may need to restart",Toast.LENGTH_LONG).show()
    }

    private fun getImportantPlaceData(searchResult : SearchResult){
        PlaceImportantData(searchResult.poi?.names?.first()?:"UnKnown Place Name",
            searchResult.address?.run { "$streetName, $localName, $country" }?:"Unknown Address",
            searchResult.distance?.inKilometers().toString().dropLast(5)+" Km",
            searchResult.position.latitude,
            searchResult.position.longitude).also {impPlaceData->
            displayPlaceInfo(impPlaceData)
        }
    }

    private fun displayPlaceInfo(placeImpData : PlaceImportantData){
        Intent(requireContext(),DisplayActivity::class.java).apply {
            putExtra("SELECTED_DISPLAY_FRAGMENT","PLACE_INFO")
            putExtra("IMPORTANT_PLACE",placeImpData)
        }.also {
            startActivity(it)
        }
    }

    override fun onResume() {
        super.onResume()
        HomeFragmentShimmer.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        HomeFragmentShimmer.stopShimmerAnimation()
    }

}