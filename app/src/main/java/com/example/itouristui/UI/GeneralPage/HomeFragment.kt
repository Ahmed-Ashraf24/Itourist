package com.example.itouristui.UI.GeneralPage


import android.content.Context
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
import com.example.itouristui.Adapters.SearchPlacesInCityAdapter
import com.example.itouristui.R
import com.example.itouristui.UI.DisplayMore.DisplayActivity
import com.example.itouristui.Utilities.CustomTextWatcher
import com.example.itouristui.Utilities.CustomTomtomCallback
import com.example.itouristui.iToursit
import com.example.itouristui.models.PlaceImportantData
import com.tomtom.sdk.common.location.GeoPoint
import com.tomtom.sdk.search.SearchOptions
import com.tomtom.sdk.search.model.geometry.CircleGeometry
import com.tomtom.sdk.search.model.result.SearchResult
import com.tomtom.sdk.search.online.OnlineSearch
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import okhttp3.Dispatcher
import java.util.*

class HomeFragment : Fragment(){

    var nearbyAdapter : GeneralPageRecViewAdapter? = null
    var suggestedAdapter : GeneralPageRecViewAdapter? = null
    lateinit var coroScope : CoroutineScope
    lateinit var searchStateFlow : MutableStateFlow<String>
    val apiKey = getApiKey("Tomtom_API_KEY", requireContext())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coroScope = CoroutineScope(Dispatchers.Main+ Job())
        searchStateFlow = MutableStateFlow("")

        HomeFragmentShimmer.startShimmerAnimation()

        NearbyPlacesRecyclerView.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)
        PopularPlacesRecyclerView.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)
        SuggestedPlacesRecyclerView.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)
        HomeSearchRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val ss: SpannableString = SpannableString("See More")
        val underLineSpan = UnderlineSpan()

        ss.setSpan(underLineSpan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tVId5.text = ss
        tVId7.text = ss




        arguments?.let {args->

            val currentPlace = iToursit.lastAddedCity.cityName
            val currentLat = iToursit.lastAddedCity.lat
            val currentLon = iToursit.lastAddedCity.lon

            CurrentLocationTextView.text = currentPlace
            requireActivity().tVId6.append(currentPlace)

            val search= OnlineSearch.create(requireContext(),"iKKlcatVgAGyYIADEYdmhjYFE6DanMP5")
            val circleGeometry = CircleGeometry(GeoPoint(currentLat,currentLon) ,500)
            val circleGeometryWide = CircleGeometry(GeoPoint(currentLat,currentLon),1000)
            val lang = Locale("ar")

            CurrentLocationSearch.addTextChangedListener(textWatcher)

            coroScope.launch {
                searchStateFlow.collectLatest {
                    delay(1500)
                    if (it.isNotBlank()){
                        try {
                            val searchOptions = SearchOptions(query = it,searchAreas = setOf(circleGeometryWide), limit = 6)
                            search.search(searchOptions , CustomTomtomCallback{
                                HomeSearchRecyclerView.visibility = View.VISIBLE
                                 val searchAdapter = SearchPlacesInCityAdapter(it.results){data->
                                    getImportantPlaceData(data,R.drawable.closed_stores_cuate)
                                }
                                HomeSearchRecyclerView.adapter = searchAdapter
                            })
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    }else{
                        if (HomeSearchRecyclerView!=null){
                            HomeSearchRecyclerView.visibility = View.GONE
                        }
                    }
                }
            }

            if (nearbyAdapter==null||iToursit.newSelectedCity){

                val searchOptionsNearby = SearchOptions(query = "store",searchAreas = setOf(circleGeometry), limit = 20)
                search.search(searchOptionsNearby, CustomTomtomCallback{ results->
                    val picturesPlaceHolders = arrayOf(R.drawable.closed_stores_pana,R.drawable.closed_stores_cuate)
                    nearbyAdapter =  GeneralPageRecViewAdapter(results.results,picturesPlaceHolders){data,resId->
                        getImportantPlaceData(data,resId)
                    }
                    if (NearbyPlacesRecyclerView!=null){
                        NearbyPlacesRecyclerView.adapter =nearbyAdapter
                    }

                    HomeFragmentShimmer.apply {
                        if (this!=null){
                            stopShimmerAnimation()
                            visibility = View.GONE
                        }
                    }
                    if (HomeFragmentNestedLayoutContainer!=null){
                        HomeFragmentNestedLayoutContainer.visibility = View.VISIBLE
                    }
                    iToursit.newSelectedCity = false
                })


            }else{
                HomeFragmentShimmer.apply {
                    stopShimmerAnimation()
                    visibility = View.GONE
                }
                HomeFragmentNestedLayoutContainer.visibility = View.VISIBLE
                NearbyPlacesRecyclerView.adapter = nearbyAdapter
            }


            if (suggestedAdapter==null||iToursit.newSelectedCity){
                val searchOptionsPopular = SearchOptions(query = "Tourist Attraction",searchAreas = setOf(circleGeometry), limit = 20, locale = lang)
                search.search(searchOptionsPopular, CustomTomtomCallback{ results->
                    val picturesPlaceHolders = arrayOf(R.drawable.travel_selfie_bro,R.drawable.bike_trial_amico)
                    suggestedAdapter = GeneralPageRecViewAdapter(results.results,picturesPlaceHolders){data,resId->
                        getImportantPlaceData(data,resId)
                    }
                    if (PopularPlacesRecyclerView!=null){
                        PopularPlacesRecyclerView.adapter = suggestedAdapter
                    }
                })
            }else{
                PopularPlacesRecyclerView.adapter = suggestedAdapter
            }

            tVId5.setOnClickListener { seeMore("store",currentLat,currentLon,R.drawable.closed_stores_cuate) }
            tVId7.setOnClickListener { seeMore("Tourist Attraction",currentLat,currentLon,R.drawable.travel_selfie_bro) }


        }?:Toast.makeText(requireContext(),"A Problem has occurred,You may need to restart",Toast.LENGTH_LONG).show()
    }

    private fun getImportantPlaceData(searchResult : SearchResult,resId:Int){
        PlaceImportantData(searchResult.searchResultId.id,
            searchResult.poi?.names?.first()?:"UnKnown Place Name",
            searchResult.address?.run { "$streetName, $localName, $country" }?:"Unknown Address",
            searchResult.distance?.inKilometers().toString().dropLast(5)+" Km",
            searchResult.position.latitude,
            searchResult.position.longitude).also {impPlaceData->
            displayPlaceInfo(impPlaceData,resId)
        }
    }

    private fun displayPlaceInfo(placeImpData : PlaceImportantData,resId:Int){
        Intent(requireContext(),DisplayActivity::class.java).apply {
            putExtra("SELECTED_DISPLAY_FRAGMENT","PLACE_INFO")
            putExtra("IMPORTANT_PLACE",placeImpData)
            putExtra("RES_ID",resId)
        }.also {
            startActivity(it)
        }
    }

    private fun seeMore(query:String,lat:Double,lon:Double,resId:Int){
        val displayListOfPlacesIntent = Intent(requireContext(), DisplayActivity::class.java).apply {
            putExtra("SELECTED_DISPLAY_FRAGMENT", "PLACES_LIST")
        }
        displayListOfPlacesIntent.apply {
            putExtra("SELECTED_CATEGORY",query)
            putExtra("LAT",lat)
            putExtra("LON",lon)
            putExtra("RES_ID",resId)
        }

        startActivity(displayListOfPlacesIntent)
    }

    private val textWatcher = CustomTextWatcher{
        runBlocking {
            searchStateFlow.emit(it)
        }
    }


    override fun onPause() {
        super.onPause()
        HomeFragmentShimmer.stopShimmerAnimation()
    }
    fun getApiKey(keyName: String, context: Context): String {
        val properties = Properties()
        return try {
            val inputStream = context.assets.open("local.properties")
            properties.load(inputStream)
            properties.getProperty(keyName, "")
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}