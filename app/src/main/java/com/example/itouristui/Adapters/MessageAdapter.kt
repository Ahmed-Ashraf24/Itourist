package com.example.itouristui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.itouristui.Message
import com.example.itouristui.R
import kotlinx.android.synthetic.main.mymessage.view.*
import kotlinx.android.synthetic.main.othersmessage.view.*
import kotlinx.android.synthetic.main.othersmessage.view.text_gchat_user_other
import kotlinx.android.synthetic.main.rating2.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

const val MY_MESSAGE = 0
const val Others_MESSAGE = 1
const val rating_message=2

var Dateflag =false
var Othersflag=true
class MessageAdapter(var messages:ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun clearData() {
        messages.clear()
        notifyDataSetChanged()
    }
    fun rearange(){
        val dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.getDefault())
        messages.sortWith { message1, message2 ->
            val date1 = dateFormat.parse(message1.date)
            val date2 = dateFormat.parse(message2.date)
            date1.compareTo(date2)

        }


    }
    class ReceivedMessageHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        val receivername:TextView = itemview.text_gchat_user_other
        val Receivedmessage: TextView=itemview.text_gchat_message_other
        val time=itemview.text_gchat_timestamp_other
        val date=itemview.text_gchat_date_other
        val personname=itemview.text_gchat_user_other
        val personicon=itemview.image_gchat_profile_other
        fun initializeclicklistener(){
            Receivedmessage.setOnClickListener {
                time.isVisible = !time.isVisible
            }
        }
        fun bind(messageContent:Message){
            personname.visibility=View.INVISIBLE
            personicon.visibility=View.INVISIBLE
            receivername.text=messageContent.sender
            Receivedmessage.text=messageContent.message
            if (Othersflag){
                personname.visibility=View.VISIBLE
                personicon.visibility=View.VISIBLE
                Othersflag=false
            }
            val currentTime = Date()
            time.text = SimpleDateFormat("h:mm a", Locale.getDefault()).format(currentTime)
            if(!Dateflag){
                date.text=SimpleDateFormat("MMMM d", Locale.getDefault()).format(currentTime)
                Dateflag=true
            }
            else{
                date.isVisible=false
            }
        }


    }
    class RatingMessageHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        val personicon=itemview.image_gchat_profile_for_rating
        val personname:TextView = itemview.text_gchat_user
        val personname2= itemview.person_name
        val personicon2=itemview.Rating_profile_other
        var date=itemview.text_gchat_date_rating
        var time=itemview.text_gchat_timestamp_rating
        var Mymessage=itemview.card_gchat_message_rating
        fun initializeclicklistener(){
            Mymessage.setOnClickListener {
                time.isVisible = !time.isVisible
            }
        }
        fun bind(){
            personname2.text=personname.text
            time.isVisible=false
            val currentTime = Calendar.getInstance().time
            time.text = SimpleDateFormat("h:mm a", Locale.getDefault()).format(currentTime)
            if(!Dateflag){
                date.text=SimpleDateFormat("MMMM d", Locale.getDefault()).format(currentTime)
                Dateflag=true
            }
            else{
                date.isVisible=false
            }
            Othersflag=true
        }

    }
    class SenderMessageHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        var date=itemview.text_gchat_date_me
        var time=itemview.text_gchat_timestamp_me
        var Mymessage: TextView=itemview.text_gchat_message_me
        fun initializeclicklistener(){
            Mymessage.setOnClickListener {
                time.isVisible = !time.isVisible
            }
        }
        fun bind(messageContent: Message){
            time.isVisible=false
            Mymessage.text=messageContent.message
            val currentTime = Calendar.getInstance().time
            time.text = SimpleDateFormat("h:mm a", Locale.getDefault()).format(currentTime)
            if(!Dateflag){
                date.text=SimpleDateFormat("MMMM d", Locale.getDefault()).format(currentTime)
                Dateflag=true
            }
            else{
                date.isVisible=false
            }
            Othersflag=true
        }


    }
    override fun getItemViewType(position: Int)= messages[position].messageType


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0-> {val view = LayoutInflater.from(parent.context).inflate(R.layout.mymessage, parent, false)
                return SenderMessageHolder(view)
            }
            1->{val view = LayoutInflater.from(parent.context).inflate(R.layout.othersmessage, parent, false)
                return ReceivedMessageHolder(view)
            }
            2->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.rating2, parent, false)
                return RatingMessageHolder(view)
            }
            else -> {throw IllegalArgumentException("Invalid view type")}
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SenderMessageHolder->{
                holder.initializeclicklistener()
                holder.bind(messages[position])

            }
            is ReceivedMessageHolder->{
                holder.initializeclicklistener()
                holder.bind(messages[position])
            }
            is RatingMessageHolder->{
                holder.initializeclicklistener()
                holder.bind()
            }

            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int =messages.size

}