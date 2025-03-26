package com.example.itouristui.ChatPage

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.MessageAdapter
import com.example.itouristui.Adapters.Others_MESSAGE
import com.example.itouristui.Message
import com.example.itouristui.Message.Companion.MY_MESSAGE
import com.example.itouristui.Message.Companion.rating_message
import com.example.itouristui.R
import kotlinx.android.synthetic.main.activity_chat.*

val messages=ArrayList<Message>()
lateinit var message:Message
val adapter= MessageAdapter(messages)

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setContentView(R.layout.activity_chat)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_gchat.layoutManager=layoutManager
        recycler_gchat.adapter= adapter

        messages.add(Message("User","Hello", Others_MESSAGE))
        messages.add(Message("User","Hi", MY_MESSAGE))
        messages.add(Message("User","Are you ready for our tour?", Others_MESSAGE))
        messages.add(Message("User","Yes sure let's go", MY_MESSAGE))
        messages.add(Message("User","Don't forget to rate me after our tour <3", Others_MESSAGE))
        messages.add(Message("User","Sure, bye <3", MY_MESSAGE))


        button_gchat_send.setOnClickListener {
            message = Message("ahmed", edit_gchat_message.text.toString(), MY_MESSAGE)
            messages.add(message)
            adapter.notifyItemInserted(messages.indexOf(message))
            edit_gchat_message.text!!.clear()
        }

        ratingbutton.setOnClickListener {
            message =Message("","", rating_message)
            messages.add(message)
            adapter.notifyItemInserted(messages.indexOf(message))
            layout_gchat_chatbox.visibility= View.INVISIBLE
        }

    }
}