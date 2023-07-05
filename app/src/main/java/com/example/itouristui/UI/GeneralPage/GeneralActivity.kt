package com.example.itouristui.UI.GeneralPage

import android.content.res.Resources
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.itouristui.FirebaseObj
import com.example.itouristui.R
import com.example.itouristui.UI.Dialogs.GpsNotEnabledDialog
import com.example.itouristui.UI.DisplayMore.RequestsFragment
import com.example.itouristui.UI.DisplayMore.SettingsFragment
import com.example.itouristui.iToursit
import com.example.itouristui.models.SimpleCityDetail
import com.example.itouristui.models.UserPlainData
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_general.*
import kotlinx.android.synthetic.main.getting_location_placeholder_layout.*
import kotlinx.android.synthetic.main.getting_location_placeholder_layout.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import pl.droidsonroids.gif.GifImageView
import java.util.*

class GeneralActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    private lateinit var locationPlaceHolderStateFlow : MutableStateFlow<Char>
    lateinit var coroScope : CoroutineScope
    private val gettingLocationPlaceHolderText = "Getting Location .,.,."


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
        MainAct3NavView.setNavigationItemSelectedListener(this)
        MainAct3NavView.itemIconTintList = null

        showCustomUI()
        coroScope = CoroutineScope(Dispatchers.Main + Job())
        FirebaseObj.uid = FirebaseObj.auth.currentUser!!.uid

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val profileFragment = ProfileFragment()

        val locationBundle = Bundle()
        val gpsNotEnabledDialog = GpsNotEnabledDialog()

        coroScope.launch(Dispatchers.IO) {

            val locationService = LocationServices.getFusedLocationProviderClient(this@GeneralActivity)
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

                    iToursit.selectedCities.add(SimpleCityDetail(geoCoding[0].adminArea,geoCoding[0].countryName,
                    p0.lastLocation!!.latitude,p0.lastLocation!!.longitude))

                    IncludedLocationPlaceHolder.visibility = View.GONE
                    GeneralActivityMainConstraint.visibility = View.VISIBLE


                    FirebaseObj.fireStore.collection("Users").document(FirebaseObj.uid).run {
                        get().addOnSuccessListener {
                            val currentUser = it.toObject(UserPlainData::class.java)
                            if (currentUser!!.city.isNullOrBlank()){
                                set(currentUser.copy(city = geoCoding[0].adminArea , country = geoCoding[0].countryName))
                            }
                        }
                    }

                    searchFragment.apply { arguments = locationBundle }
                    homeFragment.apply { arguments = locationBundle }
                        .also {
                            supportFragmentManager.beginTransaction().add(R.id.GeneralFragmentContainerView, it).commit()
                        }
                }
            }

            withContext(Dispatchers.Main){
                locationService.locationAvailability.addOnSuccessListener {
                    if (it.isLocationAvailable.not()){
                        gpsNotEnabledDialog.apply {
                            isCancelable = false
                            show(supportFragmentManager,"gpsNotEnabledDialog")
                        }
                    }else{
                        IncludedLocationPlaceHolder.visibility = View.VISIBLE
                    }
                }

                locationService.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())
            }

        }



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





    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_tour_requests_ID -> {
                val fragmentManager: FragmentManager = supportFragmentManager
                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                val fragment = RequestsFragment()
                transaction.replace(R.id.GeneralFragmentContainerView, fragment)
                transaction.addToBackStack(null)
                transaction.commit()

                return true
            }

            R.id.nav_settings_ID -> {
                val fragmentManager: FragmentManager = supportFragmentManager
                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                val fragment = SettingsFragment()
                transaction.replace(R.id.GeneralFragmentContainerView, fragment)
                transaction.addToBackStack(null)
                transaction.commit()

                return true
            }
        }
        return false
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