package com.example.itouristui.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.itouristui.R
import com.example.itouristui.UI.userlist
import com.example.itouristui.models.userslist
import kotlinx.android.synthetic.main.singleuser.*
import kotlinx.android.synthetic.main.singleuser.view.*

class usersadapter(val Userslist:ArrayList<userslist>, val listener : (String)->(Unit)) :RecyclerView.Adapter<usersadapter.UserAdapterViewHolder>() {
    class UserAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profilePicImageView = itemView.profilePic
       val  nameTextView = itemView.nameTextView
        val statusTextView = itemView.statusTextView
        val lastMessageTextView = itemView.lastMessageTextView
        val timestampTextView = itemView.timestampTextView
        fun bind(userid :String, listener2 :(String)->(Unit) ){
            itemView.setOnClickListener {
                listener2(userid)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.singleuser, parent, false)
    return UserAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapterViewHolder, position: Int) {
        holder.nameTextView.text= Userslist[position].name
        holder.statusTextView.text=Userslist[position].status
        holder.lastMessageTextView.text=Userslist[position].lastMessage
        holder.timestampTextView.text=Userslist[position].timestamp
        holder.bind(userlist[position].userid,listener)
    }

    override fun getItemCount()=Userslist.size

}

