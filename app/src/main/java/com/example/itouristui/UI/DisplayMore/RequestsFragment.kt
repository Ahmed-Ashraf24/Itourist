package com.example.itouristui.UI.DisplayMore

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.Places_adapter
import com.example.itouristui.Adapters.RequestsCardsAdapter
import com.example.itouristui.FirebaseObj
import com.example.itouristui.R
import com.example.itouristui.models.PlaceImportantData
import com.example.itouristui.models.TourRequest
import com.example.itouristui.models.UserPlainData
import com.google.firebase.FirebaseError
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.synthetic.main.fragment_liked_cities.ProfileLikedCitiesRecyclerView
import kotlinx.android.synthetic.main.fragment_requests.RequestsCardsRecViewID
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.asDeferred

class RequestsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = FirebaseObj.fireStore.collection("Users").document(FirebaseObj.uid).get().run {
            runBlocking {
                asDeferred().await().run {
                    toObject(UserPlainData::class.java)!!
                }
            }
        }

        val userList = ArrayList<UserPlainData>()
        val requestsList = ArrayList<TourRequest>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.window?.decorView?.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        FirebaseObj.fireStore.collection("Users").document(FirebaseObj.uid).run {
            collection("Tours Requests").get().addOnSuccessListener { querySnap->
                for (reqSnap in querySnap){
                    (reqSnap.data["Reference"] as DocumentReference).get().addOnSuccessListener {
                        userList.add(currentUser)
                        requestsList.add(it.toObject(TourRequest::class.java)!!)
                        if (querySnap.size()==requestsList.size){
                            with(RequestsCardsRecViewID){
                                layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
                                itemAnimator = DefaultItemAnimator()
                                adapter = RequestsCardsAdapter(requestsList, userList){}
                            }
                        }
                    }
                }
            }
        }



    }

}