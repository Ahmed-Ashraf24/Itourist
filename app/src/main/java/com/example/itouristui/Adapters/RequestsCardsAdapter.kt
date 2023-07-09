package com.example.itouristui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itouristui.R
import com.example.itouristui.models.PlaceImportantData
import com.example.itouristui.models.TourRequest
import com.example.itouristui.models.UserPlainData
import kotlinx.android.synthetic.main.request_card.view.*
import java.util.ArrayList

class RequestsCardsAdapter(val requests : List<TourRequest>,val usersData : List<UserPlainData>,val listener : (Unit)->(Unit)): RecyclerView.Adapter<RequestsCardsAdapter.ViewHolder>() {
    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val nameView = view.UserNameRequestID
        val budgetView = view.MoneyRangeRequestID
        val descriptionView = view.RequestDetailsID
        val numberOfCompanionsView = view.textView3
        val genderCompanionView = view.imageView2
        val vehiclePreferredView = view.VehicleOrNotImageView
        val arrivalDateView = view.RequestArrivalDate
        val offerSubmittedView = view.OffersSubmittedID

        fun bind(listener: (Unit) -> Unit){
            view.setOnClickListener {
                listener(Unit)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.request_card,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameView.text = usersData[position].fullName
        holder.budgetView.text = requests[position].rangeOfBudget
        holder.descriptionView.text = requests[position].description
        holder.numberOfCompanionsView.text = requests[position].numberOfPeople!!.toString()

        requests[position].tourGuideGender.takeIf { it.isNullOrBlank().not() }?.let {
            holder.genderCompanionView.setImageResource(if (it=="Male") R.drawable.baseline_male_24 else R.drawable.baseline_female_24)
        }

        requests[position].ownsVehicle.takeIf { it.isNullOrBlank().not() }?.let {
            if (it=="Yes"){
                holder.vehiclePreferredView.setImageResource(R.drawable.baseline_directions_car_24)
            }
        }

        holder.arrivalDateView.text = requests[position].arrivalDate
        holder.offerSubmittedView.text = "${requests[position].numberOfOffers} offers submitted"

        holder.bind(listener)

    }

    override fun getItemCount(): Int {
        return requests.size
    }
}