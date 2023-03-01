package com.example.itouristui.UI.GeneralPage

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.example.itouristui.FirebaseObj
import com.example.itouristui.R
import com.example.itouristui.UI.Dialogs.GpsNotEnabledDialog
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_general.*
import kotlinx.android.synthetic.main.getting_location_placeholder_layout.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import java.util.*

class GeneralActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    private lateinit var locationPlaceHolderStateFlow : MutableStateFlow<Char>
    lateinit var coroScope : CoroutineScope
    private val gettingLocationPlaceHolderText = "Getting Your Current Location . ."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
        MainAct3NavView.setNavigationItemSelectedListener(this)
        MainAct3NavView.itemIconTintList = null
        showCustomUI()
        coroScope = CoroutineScope(Dispatchers.Main + Job())
        locationPlaceHolderStateFlow = MutableStateFlow('/')
        coroScope.launch {
            locationPlaceHolderStateFlow.collectLatest {
                GettingYourLocaPlaceHolderTV.append("$it")
            }
        }
        animateLocationGettingText()

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val profileFragment = ProfileFragment()

        val locationBundle = Bundle()
        val gpsNotEnabledDialog = GpsNotEnabledDialog()

        val locationService = LocationServices.getFusedLocationProviderClient(this)
        val locationRequest = LocationRequest.Builder(5000).setMaxUpdates(1)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY).build()

        val locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                if (gpsNotEnabledDialog.isCancelable.not()){
                    gpsNotEnabledDialog.dismiss()
                }
                val geoCoding = Geocoder(this@GeneralActivity , Locale.getDefault())
                    .getFromLocation(p0.lastLocation!!.latitude,p0.lastLocation!!.longitude,1)
                locationBundle.apply {
                    putString("CURRENT_LOCATION",geoCoding[0].adminArea)
                    putDouble("LAT",p0.lastLocation!!.latitude)
                    putDouble("LON",p0.lastLocation!!.longitude)
                }

                searchFragment.apply { arguments = locationBundle }
                homeFragment.apply { arguments = locationBundle }
                    .also {
                        supportFragmentManager.beginTransaction().add(R.id.GeneralFragmentContainerView, it).commit()
                    }
            }
        }
        locationService.locationAvailability.addOnSuccessListener {
            if (it.isLocationAvailable.not()){
                gpsNotEnabledDialog.apply {
                    isCancelable = false
                    show(supportFragmentManager,"gpsNotEnabledDialog")
                }
            }
        }
        locationService.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())

        CustomBottomNavBar.setItemSelected(R.id.navHomeButtonId)
        CustomBottomNavBar.setOnItemSelectedListener { selectedNavButtonID->

           when(selectedNavButtonID){
               R.id.navHomeButtonId ->  homeFragment

               R.id.navSearchButtonId -> searchFragment

               R.id.navProfileButtonId -> profileFragment
               else -> homeFragment
           }.also{
                supportFragmentManager.beginTransaction().replace(R.id.GeneralFragmentContainerView, it).commit()
            }

        }


    }

    private fun animateLocationGettingText(){
        coroScope.launch {
            for (char in gettingLocationPlaceHolderText){
                delay(100)
                locationPlaceHolderStateFlow.emit(char)
            }
            delay(200)
            IncludedLocationPlaceHolder.visibility = View.GONE
            GeneralActivityMainConstraint.visibility = View.VISIBLE
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

        }
        return true
    }

    private fun showCustomUI(){
        window.decorView.apply {
            systemUiVisibility =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    override fun onDestroy() {
        coroScope.cancel()
        super.onDestroy()
    }


}