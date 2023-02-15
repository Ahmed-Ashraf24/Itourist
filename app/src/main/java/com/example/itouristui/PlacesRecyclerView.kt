package com.example.itouristui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.places_adapter
import com.example.itouristui.models.places
import kotlinx.android.synthetic.main.activity_places_recycler_view.*

class PlacesRecyclerView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places_recycler_view)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView1.layoutManager=layoutManager
        var places=ArrayList<places>()
        var place = places("pizza pino","","open" ,"200")
        for (i in 1..10){
            places.add(place)
        }
        recyclerView1.adapter = places_adapter(places)
    }
}