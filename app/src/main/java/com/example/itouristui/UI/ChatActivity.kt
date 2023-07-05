package com.example.itouristui.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.MessageAdapter
import com.example.itouristui.Message
import com.example.itouristui.Message.Companion.MY_MESSAGE
import com.example.itouristui.Message.Companion.rating_message
import com.example.itouristui.R
import kotlinx.android.synthetic.main.activity_chat.*

val messages=ArrayList<Message>()
lateinit var message:Message
val adapter= MessageAdapter(messages)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_gchat.layoutManager=layoutManager
        recycler_gchat.adapter=adapter
        button_gchat_send.setOnClickListener {
            message=Message("ahmed",edit_gchat_message.text.toString(), MY_MESSAGE)
            messages.add(message)
            adapter.notifyItemInserted(messages.indexOf(message))
            edit_gchat_message.text!!.clear()
            ratingbutton.setOnClickListener {
                message=Message("","", rating_message)
                messages.add(message)
                adapter.notifyItemInserted(messages.indexOf(message))
                layout_gchat_chatbox.visibility= View.INVISIBLE
            }

    }
}

}