package com.example.itouristui.UI.GeneralPage.ProfileViewPagerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.Places_adapter
import com.example.itouristui.FirebaseObj
import com.example.itouristui.R
import com.example.itouristui.models.CityData
import com.example.itouristui.models.PlaceImportantData
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.synthetic.main.fragment_liked_cities.ProfileLikedCitiesRecyclerView

class LikedCitiesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_liked_cities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val citiesForAdapter = ArrayList<CityData>()
        FirebaseObj.fireStore.collection("Users").document(FirebaseObj.uid).
        collection("Liked Cities").get().addOnSuccessListener {cities->
            for (citySnapShot in cities){
                (citySnapShot!!.data["Reference"] as DocumentReference).get().addOnSuccessListener {
                    val cityData =CityData(it.id,it["Image"].toString(),it["Liked"].toString().toInt(),it["Tours"].toString().toInt())
                    citiesForAdapter.add(cityData)
                    if (citiesForAdapter.size==cities.size()){
                        with(ProfileLikedCitiesRecyclerView){
                            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
                            adapter = Places_adapter(citiesForAdapter)
                        }
                    }
                }
            }
        }

    }

}