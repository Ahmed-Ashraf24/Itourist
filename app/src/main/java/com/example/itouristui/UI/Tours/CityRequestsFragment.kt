package com.example.itouristui.UI.Tours

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.RequestsCardsAdapter
import com.example.itouristui.FirebaseObj
import com.example.itouristui.R
import com.example.itouristui.models.TourRequest
import com.example.itouristui.models.UserPlainData
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_city_requests.*

class CityRequestsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_requests,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{args->
            val cityName = args.getString("CITY_NAME")!!
            FirebaseObj.fireStore.collection("City").document(cityName).collection("Upcoming Tours")
                .get().addOnSuccessListener {querySnap->
                    val requestsList = ArrayList<TourRequest>()
                    val usersData = ArrayList<UserPlainData>()
                    for (reqSnapShot in querySnap){
                        reqSnapShot.toObject(TourRequest::class.java).run {
                            userRef!!.get().addOnSuccessListener { userSnap->
                                val user = userSnap.toObject(UserPlainData::class.java)
                                requestsList.add(this)
                                usersData.add(user!!)

                                if (usersData.size==querySnap.size()){
                                    setupRequestsRecyclerView(requestsList,usersData)
                                }
                            }
                        }
                    }
                }
        }


    }

    private fun setupRequestsRecyclerView(requestsList : ArrayList<TourRequest> , usersDataList : ArrayList<UserPlainData>){
        with(CityRequestsRecyclerView){
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
            adapter = RequestsCardsAdapter(requestsList,usersDataList){
                parentFragmentManager.beginTransaction().replace(R.id.ToursFragmentContainerView,OffersFragment()).addToBackStack(null).commit()
            }
        }
    }
}