package com.example.itouristui.UI.GeneralPage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.CategoriesPlaceHolderRecViewAdapter
import com.example.itouristui.Adapters.CitiesSearchRecViewAdapter
import com.example.itouristui.Data.Remote.CityCountryApiObject
import com.example.itouristui.UI.DisplayMore.PlacesListFragment
import com.example.itouristui.R
import com.example.itouristui.UI.DisplayMore.DisplayActivity
import com.example.itouristui.Utilities.CategoriesPlaceHolders
import com.example.itouristui.Utilities.CustomRetrofitCallBack
import com.example.itouristui.Utilities.CustomTextWatcher
import com.example.itouristui.iToursit
import com.example.itouristui.models.CityDetails
import com.example.itouristui.models.SimpleCityDetail
import kotlinx.android.synthetic.main.activity_general.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest


class SearchFragment : Fragment() {

    lateinit var coroScope : CoroutineScope
    lateinit var queryStateFlow: MutableStateFlow<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coroScope = CoroutineScope(Dispatchers.Main)
        queryStateFlow = MutableStateFlow("")


        SearchCityCountryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        SearchCityCountryRecyclerView.itemAnimator = DefaultItemAnimator()

        val displayListOfPlacesIntent = Intent(requireContext(), DisplayActivity::class.java).apply {
            putExtra("SELECTED_DISPLAY_FRAGMENT", "PLACES_LIST")
        }
        with(CategoriesRecyclerView) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            itemAnimator = DefaultItemAnimator()
            adapter = CategoriesPlaceHolderRecViewAdapter(CategoriesPlaceHolders.categoriesOfPlaces){

                arguments?.let { args->
                    displayListOfPlacesIntent.apply {
                        putExtra("SELECTED_CATEGORY",it)
                        putExtra("LAT",args.getDouble("LAT"))
                        putExtra("LON",args.getDouble("LON"))
                    }

                    startActivity(displayListOfPlacesIntent)

                }?: Toast.makeText(requireContext(),"An Error Occurred",Toast.LENGTH_SHORT).show()
            }
        }

        GeneralSearchEditText.addTextChangedListener(textWatcherAnimator)
        coroScope.launch{
            queryStateFlow.collectLatest {
                delay(1500)
                if (it.isNotBlank()){
                    CityCountryApiObject.cityCountryApiInterface.getCities(prefixName = it)
                        .enqueue(CustomRetrofitCallBack<List<CityDetails>>{successfulRes->
                            SearchCityCountryRecyclerView.adapter = CitiesSearchRecViewAdapter(successfulRes.body()!!){
                                val cityOverviewFragment = CityOverviewFragment().apply {
                                    val bundle = Bundle()
                                    bundle.putString("CITY","${it.name}, ${it.country.name}")
                                    bundle.putDouble("LAT",it.coordinates.latitude)
                                    bundle.putDouble("LON",it.coordinates.longitude)
                                    arguments=bundle
                                }
                                requireActivity().CustomBottomNavBar.visibility = View.GONE
                                parentFragmentManager.beginTransaction().replace(R.id.GeneralFragmentContainerView,cityOverviewFragment).addToBackStack(null).commit()
                                /*iToursit.selectedCities.add(SimpleCityDetail(it.name,it.country.name,
                                    it.coordinates.latitude,it.coordinates.longitude))
                                iToursit.newSelectedCity=true

                                val input = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                input.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken,0)
                                Thread.sleep(500)
                                requireActivity().CustomBottomNavBar.setItemSelected(R.id.navHomeButtonId)*/
                            }
                        })
                }else{
                    SearchCityCountryRecyclerView.adapter = CitiesSearchRecViewAdapter(emptyList()){

                    }
                }
            }
        }
    }

    override fun onDestroy() {
        coroScope.cancel()
        super.onDestroy()

    }

    private val textWatcherAnimator = CustomTextWatcher{ typedText->
        runBlocking {
            queryStateFlow.emit(typedText)
        }
    }

}