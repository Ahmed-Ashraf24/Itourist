package com.example.itouristui.ChatPage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.usersadapter
import com.example.itouristui.Message
import com.example.itouristui.R
import com.example.itouristui.models.UserPlainData
import com.example.itouristui.models.firebaseUsersDatabase
import com.example.itouristui.models.firebase_message
import com.example.itouristui.models.firebase_users
import com.example.itouristui.models.userslist
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chat.button_gchat_send
import kotlinx.android.synthetic.main.activity_chat.edit_gchat_message
import kotlinx.android.synthetic.main.activity_chat.layout_gchat_chatbox
import kotlinx.android.synthetic.main.activity_chat.ratingbutton
import kotlinx.android.synthetic.main.activity_chat.recycler_gchat
import kotlinx.android.synthetic.main.activity_chatusers.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.google.firebase.database.*
import kotlin.collections.ArrayList

val userlist=ArrayList<userslist>()
lateinit var conversationid: String
val db=FirebaseDatabase.getInstance()
val auth= FirebaseAuth.getInstance()

class ChatusersActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val convKey=FirebaseDatabase.getInstance().getReference("conversations").push()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setContentView(R.layout.activity_chatusers)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        userRecyclerView.layoutManager=layoutManager
        val userid = intent.getStringExtra("otheruserid")
        conversationid=""
       val convid=convKey.key!!
        val usersref=db.getReference("users").child(auth.currentUser!!.uid).child(userid!!)
        usersref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val otherUserSnapshot = snapshot.getValue(firebaseUsersDatabase::class.java)
                    val conversationId = otherUserSnapshot!!.Conversationid
                    conversationid =conversationId


                }
                else{
                    db.getReference("users").child(userid).child(auth!!.currentUser!!.uid).
                    setValue(
                        firebaseUsersDatabase(
                        convid,ServerValue.TIMESTAMP.toString())
                    )

                    db.getReference("users").child(auth.currentUser!!.uid).child(userid).
                    setValue(firebaseUsersDatabase(convid!!,ServerValue.TIMESTAMP.toString()))
                    conversationid=convid
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        val convref=db.getReference("conversations").child(conversationid!!)
        val messageref=convref.child(convref.push().toString().substringAfterLast("-"))

        convref.orderByChild("time").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (messageSnapshot in snapshot.children) {
                    for(messagesshot in messageSnapshot.children){
                        val mkey = messageSnapshot.value
                        mkey
                        val senderId =
                            messagesshot.child("senderid").value.toString()
                        val message2 =
                            messagesshot.child("message").value.toString()
                        val date= messagesshot.child("date").value.toString()

                        val time = messagesshot.child("time").value.toString()

                        val firebasemessage =
                            firebase_message(senderId!!, message2!!, time!!,date)
                        val name =
                            FirebaseFirestore.getInstance().collection("users")
                                .get().addOnSuccessListener {

                                    for(docoment in it){

                                        if(docoment.id==firebasemessage.senderid){
                                            val fdb = docoment.toObject(UserPlainData::class.java)
                                            if (FirebaseAuth.getInstance().currentUser!!.uid == firebasemessage.senderid)
                                            {
                                                message = Message(
                                                    docoment.toObject(UserPlainData::class.java)!!.fullName!!,
                                                    firebasemessage.message,
                                                    Message.MY_MESSAGE
                                                    ,firebasemessage.date)
                                                messages.add(message)
                                                adapter.notifyItemInserted(messages.indexOf(message))
                                                recycler_gchat.layoutManager=layoutManager
                                                recycler_gchat.adapter=adapter


                                            } else {
                                                message = Message(
                                                    docoment.toObject(UserPlainData::class.java)!!.fullName!!,
                                                    firebasemessage.message,
                                                    Message.Others_MESSAGE
                                                    ,firebasemessage.date)
                                                messages.add(message)
                                                adapter.notifyItemInserted(messages.indexOf(message))
                                                recycler_gchat.layoutManager=layoutManager
                                                recycler_gchat.adapter=adapter

                                            }
                                        }
                                    }
                                }
                    }



                    // Do something with the message data

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        convref.orderByChild("time").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                adapter.clearData()
                for (messageSnapshot in snapshot.children) {

                    val senderId = messageSnapshot.child("senderid").value.toString()
                    val message2 = messageSnapshot.child("message").value.toString()
                    val time = messageSnapshot.child("time").value.toString()
                    val date= messageSnapshot.child("date").value.toString()
                    val firebasemessage = firebase_message(senderId, message2, time,date)

                    FirebaseFirestore.getInstance().collection("users")
                        .document(senderId)
                        .get()
                        .addOnSuccessListener { documentSnapshot ->
                            val firebaseUser = documentSnapshot.toObject(UserPlainData::class.java)

                            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

                            val messageType = if (currentUserId == senderId) Message.MY_MESSAGE else Message.Others_MESSAGE

                            val message = Message(firebaseUser?.fullName!!,
                                firebasemessage?.message!!, messageType,date)
                            messages.add(message)
                            adapter.notifyItemInserted(messages.indexOf(message))
                            recycler_gchat.layoutManager = layoutManager
                            recycler_gchat.adapter = adapter
                            adapter.rearange()
                        }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        recycler_gchat.layoutManager=layoutManager
        recycler_gchat.adapter=adapter
        adapter.rearange()
        button_gchat_send.setOnClickListener {
            val date2= Date()

            message=Message("ahmed",edit_gchat_message.text.toString(),
                Message.MY_MESSAGE,
                SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.getDefault()).format(date2))
            messages.add(message)
            adapter.notifyItemInserted(messages.indexOf(message))
            edit_gchat_message.text!!.clear()

            convref.child(conversationid).child(convref.child(conversationid).push().key!!)
                .setValue(firebase_message(FirebaseAuth.getInstance().currentUser!!.uid,
                    message = message.message,ServerValue.TIMESTAMP.toString(),
                    SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.getDefault())
                        .format(date2))).addOnSuccessListener {
                    Toast.makeText(this,"message sent successfully",Toast.LENGTH_SHORT).show()

                }

        }
        ratingbutton.setOnClickListener {
            val date2= Date()
            message=Message("","", Message.rating_message,
                SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.getDefault()).format(date2))
            messages.add(message)
            adapter.notifyItemInserted(messages.indexOf(message))
            layout_gchat_chatbox.visibility= View.INVISIBLE

        }

        // Conversation ID exists, use it as needed
        val firestore= FirebaseFirestore.getInstance()
        val Collectionref=firestore.collection("users")
           Collectionref.get().addOnSuccessListener {
            for (document in it) {
                val data = document.toObject(firebase_users::class.java)
                userlist.add(userslist(data.name,data.status,data.pic,document.id,data.email,"hi","11:45"))

            }
             /*  dummy data */
/*

        userlist.add(userslist("User name 1","Online","","1","UserName","Sure, Bye","12:00"))
        userlist.add(userslist("User name 2","Online","","2","UserName","Hello","11:00"))
        userlist.add(userslist("User name 3","Online","","3","UserName","Let's go","10:00"))
        userlist.add(userslist("User name 4","Online","","4","UserName","I'm waiting for you","9:00"))
        userlist.add(userslist("User name 5","Online","","5","UserName","FR?","8:00"))
        userlist.add(userslist("User name 6","Online","","6","UserName","Okay cya","7:00"))
        userlist.add(userslist("User name 7","Online","","7","UserName","Im on my way","6:00"))
        userlist.add(userslist("User name 8","Online","","8","UserName","Hi","5:00"))
        userlist.add(userslist("User name 9","Online","","9","UserName","Ok","4:00"))
        userlist.add(userslist("User name 10","Online","","10","UserName","LoL","3:00"))
*/


        val adapter= usersadapter(userlist) {
               val intent=Intent(applicationContext, ChatActivity::class.java)
                intent.putExtra("otheruserid",it)
                startActivity(intent)
                }





            userRecyclerView.adapter=adapter
            Toast.makeText(this, userlist[0].useremail +" :  "+ userlist[0].userid,Toast.LENGTH_SHORT).show()

        }
            .addOnFailureListener { exception ->
                Toast.makeText(this,exception.toString(),Toast.LENGTH_SHORT).show()
            }


    }
}