package com.example.itouristui.UI

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.usersadapter
import com.example.itouristui.R
import com.example.itouristui.models.firebase_users
import com.example.itouristui.models.userslist
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chatusers.*
import  com.example.itouristui.UI.ChatActivity

val userlist=ArrayList<userslist>()

class ChatusersActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setContentView(R.layout.activity_chatusers)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        userRecyclerView.layoutManager=layoutManager
        val firestore= FirebaseFirestore.getInstance()
        /*val Collectionref=firestore.collection("users")
        val auth=FirebaseAuth.getInstance()
           Collectionref.get().addOnSuccessListener {
            for (document in it) {
                val data = document.toObject(firebase_users::class.java)
                userlist.add(userslist(data.name,data.status,data.pic,document.id,data.email,"hi","11:45"))

            }*/

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


        val adapter= usersadapter(userlist) {
               val intent=Intent(applicationContext,ChatActivity::class.java)
                intent.putExtra("otheruserid",it)
                startActivity(intent)
                }





            userRecyclerView.adapter=adapter
            Toast.makeText(this, userlist[0].useremail +" :  "+ userlist[0].userid,Toast.LENGTH_SHORT).show()

        }
            /*.addOnFailureListener { exception ->
                Toast.makeText(this,exception.toString(),Toast.LENGTH_SHORT).show()
            }


    }*/
}