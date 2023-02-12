package com.example.itouristui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itouristui.models.CategoriesOfPlaces
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    private var readyToAnimate = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesPlaceHolders = listOf(
            CategoriesOfPlaces("shopping",R.drawable.shopping_illustr_undraw),
            CategoriesOfPlaces("restaurant",R.drawable.restaurent_illustr_undraw),
            CategoriesOfPlaces("market",R.drawable.markets_illustr_undraw),
            CategoriesOfPlaces("coffee",R.drawable.coffe_illustr_undraw),
            CategoriesOfPlaces("points of interest",R.drawable.poi_illustr_undraw),
            CategoriesOfPlaces("gardens",R.drawable.gardens_illustr_undraw),
            CategoriesOfPlaces("studio",R.drawable.studio_illustr_undraw),
            CategoriesOfPlaces("technological",R.drawable.electronics_illustr_undraw),
            CategoriesOfPlaces("hospital",R.drawable.hospital_illustr_undraw),
            CategoriesOfPlaces("jewels",R.drawable.jewllery_illust_undraw),
            CategoriesOfPlaces("governmental",R.drawable.govenmental_illust_undraw),
        )

        with(CategoriesRecyclerView){
            layoutManager = GridLayoutManager(requireContext() , 2 )
            itemAnimator = DefaultItemAnimator()
            adapter = CategoriesPlaceHolderRecViewAdapter(categoriesPlaceHolders)
        }

        ActualAnySearchEditText.addTextChangedListener(textWatcherAnimator)
    }

    val textWatcherAnimator = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0.isNullOrBlank().not() && readyToAnimate){

            }else if (p0.isNullOrBlank()){

            }
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    }


}