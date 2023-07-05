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
import com.example.itouristui.FirebaseObj
import com.example.itouristui.R
import com.example.itouristui.models.PlaceImportantData
import com.example.itouristui.models.ReviewData
import com.example.itouristui.models.UserPlainData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.fragment_item.*
import kotlinx.android.synthetic.main.review_item.*

class PlaceInfoFragment : Fragment() {

    var overallRate = 0f
    var numberOfReviews = 0
    var myRate = 0
    var myPreviousRate = 0
    var hasReviewed = false
    lateinit var placeRef : DocumentReference

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
            var placeXID = ""
            val placeData = it.getParcelable<PlaceImportantData>("IMPORTANT_PLACE").also {
                PlaceInfoFragmentNameTV.text = it!!.nameOfPlace
                PlaceInfoFragmentAddressTV.text = it.address
                PlaceInfoFragmentDistanceTV.text = it.distanceAway
                placeXID = it.id
            }
            val resId = it.getInt("RES_ID")
            PlaceHolderOfPlaceImageView.setImageResource(resId)

            placeRef = FirebaseObj.fireStore.collection("Places").document(placeXID)



            placeRef.run {
                get().addOnSuccessListener {docSnap->
                    if (docSnap.exists()){
                        setupPreInsertedPlace(docSnap)
                    }else{
                        insertNewPlace()
                    }
                }
            }


            ConfirmMyReview.setOnClickListener {
                if ((PlaceMyReviewEditText.text?.toString()?:"").isNotBlank()){
                    myRate = ReviewerRangeBar.rightPinValue.toInt()
                    numberOfReviews += (if (hasReviewed) 0 else 1)
                    placeRef.run {
                        set(
                            hashMapOf(
                                "Rate" to (overallRate-myPreviousRate+myRate)/numberOfReviews ,
                                "Number Of Reviews" to numberOfReviews
                            )
                        )

                        collection("Reviews").document(FirebaseObj.uid).set(
                            hashMapOf(
                                "Rate" to myRate,
                                "Review" to PlaceMyReviewEditText.text.toString(),
                                "Reference" to FirebaseObj.fireStore.collection("Users").document(FirebaseObj.uid)
                            )
                        ).addOnSuccessListener {
                            showMyReview(PlaceMyReviewEditText.text.toString())
                        }
                    }

                    FirebaseObj.fireStore.collection("Users").document(FirebaseObj.uid)
                        .collection("Places Reviews").document(placeXID).set(
                            hashMapOf(
                                "Rate" to myRate,
                                "Review" to PlaceMyReviewEditText.text.toString(),
                                "Reference" to placeRef
                            )
                        )
                }
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

    }

    private fun setupPreInsertedPlace(docSnap : DocumentSnapshot){
        docSnap.data!!.also {
            overallRate = it["Rate"].toString().toFloat()
            numberOfReviews = it["Number Of Reviews"].toString().toInt()

            OverallRateTextView.text = it["Rate"].toString()

            val ss = SpannableString(OverallRateTextView.text)
            ss.setSpan(RelativeSizeSpan(2f),0,1,0)
            OverallRateTextView.text = ss

            for (i in 1..it["Rate"].toString().substringBefore('.').toInt()){
                (linearLayoutCompat2.getChildAt(i-1) as androidx.appcompat.widget.AppCompatImageButton).setImageResource(R.drawable.ic_baseline_star_24)
            }

            showMyReview("")
            showAllReviews()
        }
    }

    private fun insertNewPlace(){
        placeRef.run {
            set(hashMapOf(
                    "Rate" to 0.0,
                    "Number Of Reviews" to 0
                ))
        }

        PlaceMyReviewEditText.visibility = View.VISIBLE
        ReviewerRangeBar.visibility = View.VISIBLE
        ConfirmMyReview.visibility = View.VISIBLE

    }

    private fun showMyReview(review:String){

        if (review.isNotBlank()){
            PlaceMyReviewTextView.visibility = View.VISIBLE
            MyRating.visibility = View.VISIBLE

            PlaceMyReviewEditText.visibility = View.GONE
            ReviewerRangeBar.visibility = View.GONE
            ConfirmMyReview.visibility = View.GONE

            PlaceMyReviewTextView.text = review
            MyRating.rating = myRate.toFloat()

        }else{
            placeRef.collection("Reviews").document(FirebaseObj.uid).get().addOnSuccessListener {
                if (it.exists()){
                    PlaceMyReviewTextView.visibility = View.VISIBLE
                    MyRating.visibility = View.VISIBLE

                    hasReviewed = true
                    myPreviousRate = it.data!!["Rate"].toString().toInt()
                    PlaceMyReviewTextView.text = it.data!!["Review"].toString()
                    MyRating.rating = myPreviousRate.toFloat()

                }else{
                    PlaceMyReviewEditText.visibility = View.VISIBLE
                    ReviewerRangeBar.visibility = View.VISIBLE
                    ConfirmMyReview.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showAllReviews(){
        val reviews = ArrayList<ReviewData>()
        placeRef.collection("Reviews").get().addOnSuccessListener {
            if (it.size()==0) return@addOnSuccessListener
            for (docSnapshot in it.documents){
                val (rate,review) = docSnapshot.data!!.run {
                    Pair<Int,String>(get("Rate").toString().toInt(),get("Review").toString())
                }

               ( docSnapshot.data!!.get("Reference") as DocumentReference).get().addOnSuccessListener { userDoc->
                   val user = userDoc.toObject(UserPlainData::class.java)

                   reviews.add(ReviewData(user!!.fullName,"${user.city},${user.country}",review,rate))
                   println("size "+reviews.size)
                    if (it.size()==reviews.size){
                        NoDateImageView.visibility = View.GONE
                        with(ReviewsRecyclerView){
                            visibility = View.VISIBLE
                            layoutManager = LinearLayoutManager(requireContext())
                            itemAnimator = DefaultItemAnimator()
                            adapter = ReviewsRecViewAdapter(reviews)
                        }
                    }
               }
            }


        }
    }

}