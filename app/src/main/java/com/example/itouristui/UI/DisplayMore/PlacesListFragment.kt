package com.example.itouristui.UI.DisplayMore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.places_adapter
import com.example.itouristui.R
import com.example.itouristui.Utilities.CustomTomtomCallback
import com.example.itouristui.models.places
import com.tomtom.sdk.common.location.GeoPoint
import com.tomtom.sdk.search.SearchOptions
import com.tomtom.sdk.search.model.geometry.CircleGeometry
import com.tomtom.sdk.search.online.OnlineSearch
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
            val places=ArrayList<places>()

            val searchOptions = SearchOptions(query = query.toString(), searchAreas = setOf(circleGeometry), limit = 20, locale = lang)
            search.search(searchOptions,CustomTomtomCallback{result->

                result.results.forEach {
                    it.poi?.let { result->
                        places.add(places(result.names.first(),it.address.toString(),"open" ,it.distance!!.inKilometers().toString().dropLast(5)+"km away"))
                    }
                }
                val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                recyclerView1.layoutManager=layoutManager
                recyclerView1.adapter = places_adapter(places)
            })

        }
    }

}