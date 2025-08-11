package com.example.itouristui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplicationrealtimedatabase.Message
import com.example.chatapplicationrealtimedatabase.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList




class MessageAdapter(var messages:ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dateFlag =false
    var othersFlag=true

    fun rearrange(){
        val dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.getDefault())
        messages.sortWith { message1, message2 ->
            val date1 = dateFormat.parse(message1.date)
            val date2 = dateFormat.parse(message2.date)
            date1.compareTo(date2)

        }


    }
    inner class ReceivedMessageHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        val receivername:TextView = itemview.findViewById(R.id.text_gchat_user_other)
        val Receivedmessage: TextView = itemview.findViewById(R.id.text_gchat_message_other)
        val time: TextView = itemview.findViewById(R.id.text_gchat_timestamp_other)
        val date: TextView = itemview.findViewById(R.id.text_gchat_date_other)
        val personname: TextView = itemview.findViewById(R.id.text_gchat_user_other)
        val personicon: ImageView = itemview.findViewById(R.id.image_gchat_profile_other)
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
            if (othersFlag){
                personname.visibility=View.VISIBLE
                personicon.visibility=View.VISIBLE
                othersFlag=false
            }
            val currentTime = Date()
            time.text = SimpleDateFormat("h:mm a", Locale.getDefault()).format(currentTime)
            if(!dateFlag){
                date.text=SimpleDateFormat("MMMM d", Locale.getDefault()).format(currentTime)
                dateFlag=true
            }
            else{
                date.isVisible=false
            }
        }


    }
    inner class RatingMessageHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        val personicon: ImageView = itemview.findViewById(R.id.image_gchat_profile_for_rating)
        val personname: TextView = itemview.findViewById(R.id.text_gchat_user)
        val personname2: TextView = itemview.findViewById(R.id.person_name)
        val personicon2: ImageView = itemview.findViewById(R.id.Rating_profile_other)
        var date: TextView = itemview.findViewById(R.id.text_gchat_date_rating)
        var time: TextView = itemview.findViewById(R.id.text_gchat_timestamp_rating)
        var Mymessage: CardView = itemview.findViewById(R.id.card_gchat_message_rating)
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
            if(!dateFlag){
                date.text=SimpleDateFormat("MMMM d", Locale.getDefault()).format(currentTime)
                dateFlag=true
            }
            else{
                date.isVisible=false
            }
            othersFlag=true
        }

    }
    inner class SenderMessageHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        var date: TextView = itemview.findViewById(R.id.text_gchat_date_me)
        var time: TextView = itemview.findViewById(R.id.text_gchat_timestamp_me)
        var Mymessage: TextView = itemview.findViewById(R.id.text_gchat_message_me)
        fun initializeclicklistener(){
            Mymessage.setOnClickListener {
                time.isVisible = !time.isVisible
            }
        }
        fun bind(messageContent: Message){
            time.isVisible=false
            Mymessage.text=messageContent.message
            val currentTime = Calendar.getInstance().time
            time.text = messageContent.date
            if(!dateFlag){
                date.text=messageContent.date
                dateFlag=true
            }
            else{
                date.isVisible=false
            }
            othersFlag=true
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