package com.example.itouristui.UI.GeneralPage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.itouristui.Adapters.CityPicturesRecyclerViewAdapter
import com.example.itouristui.Data.Remote.UnsplashData
import com.example.itouristui.Data.Remote.WikipediaData
import com.example.itouristui.R
import com.example.itouristui.Utilities.CustomRetrofitCallBack
import com.example.itouristui.iToursit
import com.example.itouristui.models.SimpleCityDetail
import kotlinx.android.synthetic.main.activity_general.*
import kotlinx.android.synthetic.main.fragment_city_overview.*
import org.json.JSONArray
import org.json.JSONObject

class CityOverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_overview,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       arguments?.let {bundle->
           val cityName = bundle.getString("CITY","")
           CityNameTextView.text = cityName

           WikipediaData.wikiApiImp.getCityDataByName(cityName.substringBefore(',')).enqueue(
               CustomRetrofitCallBack<String>{

                   val cityPage = JSONObject(it.body()?:"").getJSONObject("query").getJSONArray("pages")
                   val cityDescription = cityPage.getJSONObject(0).getString("extract")
                   CityDetailsTextView.text = cityDescription
               }
           )

           WikipediaData.wikiApiImp.getCityImage(cityName.substringBefore(',')).enqueue(
               CustomRetrofitCallBack<String>{

                   val cityImagePage = JSONObject(it.body()?:"").getJSONObject("query").getJSONArray("pages")
                   val cityImage = cityImagePage.getJSONObject(0).getJSONObject("thumbnail").getString("source")
                   Glide.with(requireContext()).load(cityImage).into(CityImageView)
               }
           )

           UnsplashData.unsplashInterface.getCityImages(cityName.replace(',',' ')).enqueue(
               CustomRetrofitCallBack<String>{
                   val mutableListOfImages = mutableListOf<String>()
                   val imagesRes = JSONObject(it.body()?:"").getJSONArray("results")
                   for (i in 0 until imagesRes.length()){
                       mutableListOfImages.add(imagesRes.getJSONObject(i).getJSONObject("urls").getString("regular"))
                   }

                   with(OtherImagesRecyclerView){
                       layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                       itemAnimator = DefaultItemAnimator()
                       adapter = CityPicturesRecyclerViewAdapter(mutableListOfImages.toList()){pic->
                           Glide.with(requireContext()).load(pic).into(CityImageView)
                       }
                   }

               }
           )

           RequestTourGuideButton.setOnClickListener {

           }

           NavToCityButton.setOnClickListener {
               val city = cityName.substringBefore(',')
               val country = cityName.substringAfter(',').trim()
               iToursit.selectedCities.add(SimpleCityDetail(city,country, bundle.getDouble("LAT",0.0)
                   ,bundle.getDouble("LON",0.0))
               )
               iToursit.newSelectedCity=true

               parentFragmentManager.popBackStack()
               requireActivity().CustomBottomNavBar.apply {
                   visibility = View.VISIBLE
                   setItemSelected(R.id.navHomeButtonId)
               }
           }
       }
    }
}