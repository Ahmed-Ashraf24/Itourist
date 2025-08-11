package com.example.itouristui.ChatPage

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapplicationrealtimedatabase.FirebaseMessage
import com.example.chatapplicationrealtimedatabase.FirebaseUsersDatabase
import com.example.itouristui.Adapters.MessageAdapter
import com.example.itouristui.Adapters.Others_MESSAGE
import com.example.itouristui.Message
import com.example.itouristui.Message.Companion.MY_MESSAGE
import com.example.itouristui.Message.Companion.rating_message
import com.example.itouristui.R
import com.example.itouristui.databinding.ActivityChatBinding
import com.example.itouristui.models.UserPlainData
import com.example.itouristui.models.userslist
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



class ChatActivity : AppCompatActivity() {
    private val userList = ArrayList<userslist>()
    private lateinit var conversationid: String
    private val db = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var message: Message
    private lateinit var binding: ActivityChatBinding
    private val messages = ArrayList<Message>()
    private lateinit var adapter: MessageAdapter
    private var name = ""
    private var liveListenerFlag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getStringExtra("name")?.let { name = it }
        binding.username.text = name
        binding.recyclerGchat.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdapter(messages)
        binding.recyclerGchat.adapter = adapter

        val userid = intent.getStringExtra("otheruserid")
        if (userid == null) {
            Toast.makeText(this, "User ID missing", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val convKey = db.getReference("conversations").push()
        val usersRef = db.getReference("users").child(auth.currentUser!!.uid).child(userid)

        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val otherUserSnapshot = snapshot.getValue(FirebaseUsersDatabase::class.java)
                    conversationid = otherUserSnapshot!!.Conversationid
                    val serverDate = Date(otherUserSnapshot.lastmessagetime)
                    val formattedDate =
                        SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault()).format(
                            serverDate
                        )

                    binding.lastSeenTextView.text = formattedDate
                } else {
                    val newConvId = convKey.key!!
                    val userData = mapOf(
                        "Conversationid" to newConvId,
                        "lastmessagetime" to ServerValue.TIMESTAMP
                    )
                    db.getReference("users").child(userid).child(auth.currentUser!!.uid)
                        .setValue(userData)
                    db.getReference("users").child(auth.currentUser!!.uid).child(userid)
                        .setValue(userData)
                    conversationid = newConvId
                }
                binding.backButton.setOnClickListener {
                    finish()
                }
                val convRef = db.getReference("conversations").child(conversationid)
                setupMessageListeners(convRef)
                setupSendButton(convRef)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChatActivity, "Error loading chat", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setupSendButton(convRef: DatabaseReference) {
        binding.buttonGchatSend.setOnClickListener {
            val date2 = Date()
            message = Message(
                "ahmed",
                binding.editGchatMessage.text.toString(),
                Message.MY_MESSAGE,
                SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.getDefault()).format(date2)
            )
//            messages.add(message)
//            adapter.notifyItemInserted(messages.size - 1)
            binding.editGchatMessage.text!!.clear()

            val msgRef = convRef.push()
            msgRef.setValue(
                mapOf(
                    "senderid" to auth.currentUser!!.uid,
                    "message" to message.message,
                    "date" to message.date,
                    "time" to ServerValue.TIMESTAMP
                )
            ).addOnSuccessListener {
                val currentUserId = auth.currentUser!!.uid
                val otherUserId = intent.getStringExtra("otheruserid")!!

                val usersRef = db.getReference("users")
                usersRef.child(currentUserId).child(otherUserId).child("lastmessagetime")
                    .setValue(ServerValue.TIMESTAMP)
                usersRef.child(otherUserId).child(currentUserId).child("lastmessagetime")
                    .setValue(ServerValue.TIMESTAMP)

                Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupMessageListeners(convRef: DatabaseReference) {
        convRef.orderByChild("time").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val tempMessages = mutableListOf<Message>()
                    for (child in snapshot.children) {
                        val msg = child.getValue(FirebaseMessage::class.java) ?: continue

                        val doc = FirebaseFirestore.getInstance().collection("Users")
                            .document(msg.senderid).get().await()

                        val user = doc.toObject(UserPlainData::class.java)
                        val type =
                            if (auth.currentUser!!.uid == msg.senderid) Message.MY_MESSAGE else Message.Others_MESSAGE
                        val chatMsg = Message(user?.fullName ?: "Unknown", msg.message, type, msg.date)
                        tempMessages.add(chatMsg)

                    }

                    withContext(Dispatchers.Main) {
                        messages.clear()
                        messages.addAll(tempMessages)
                        adapter.notifyDataSetChanged()
                        liveListenerFlag = true
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        convRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (!liveListenerFlag) return
                val msg = snapshot.getValue(FirebaseMessage::class.java) ?: return
                Log.d("on child added", "onChildAdded is triggered")
                lifecycleScope.launch(Dispatchers.IO) {
                    val doc = FirebaseFirestore.getInstance().collection("Users")
                        .document(msg.senderid).get().await()

                    val user = doc.toObject(UserPlainData::class.java)
                    val type =
                        if (auth.currentUser!!.uid == msg.senderid) Message.MY_MESSAGE else Message.Others_MESSAGE
                    val newMsg = Message(user?.fullName ?: "Unknown", msg.message, type, msg.date)

                    withContext(Dispatchers.Main) {

                        messages.add(newMsg)
                        messages.forEach {
                            Log.d("from on child added", it.message)
                        }
                        adapter.notifyItemInserted(messages.size - 1)

                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }


}