package com.example.itouristui.UI.GeneralPage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itouristui.Adapters.CategoriesPlaceHolderRecViewAdapter
import com.example.itouristui.Adapters.CitiesSearchRecViewAdapter
import com.example.itouristui.Data.Remote.CityCountryApiObject
import com.example.itouristui.R
import com.example.itouristui.Utilities.CategoriesPlaceHolders
import com.example.itouristui.Utilities.CustomRetrofitCallBack
import com.example.itouristui.Utilities.CustomTextWatcher
import com.example.itouristui.models.CategoriesOfPlaces
import com.example.itouristui.models.CityDetails
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder


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


        with(CategoriesRecyclerView) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            itemAnimator = DefaultItemAnimator()
            adapter = CategoriesPlaceHolderRecViewAdapter(CategoriesPlaceHolders.categoriesOfPlaces)
        }

        GeneralSearchEditText.addTextChangedListener(textWatcherAnimator)
        coroScope.launch{
            queryStateFlow.collectLatest {
                delay(1500)
                if (it.isNotBlank()){
                    CityCountryApiObject.cityCountryApiInterface.getCities(prefixName = it)
                        .enqueue(CustomRetrofitCallBack<List<CityDetails>>{successfulRes->
                            SearchCityCountryRecyclerView.adapter = CitiesSearchRecViewAdapter(successfulRes.body()!!)
                        })
                }else{
                    SearchCityCountryRecyclerView.adapter = CitiesSearchRecViewAdapter(emptyList())
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