package com.example.itouristui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ViewAnimator
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
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

        val popularSearch = listOf<String>("Chicken" , "Pi" , "Burger" , "Mcdonalds" , "Shawrma","Pasta","Crape","Dessert")

        with(SearchRecView){
            layoutManager = FlexboxLayoutManager(requireContext()).apply {
                flexDirection = FlexDirection.ROW

            }
            itemAnimator = DefaultItemAnimator()
            adapter = PopularSearchRecViewAdapter(popularSearch)
        }




        ActualAnySearchEditText.addTextChangedListener(textWatcherAnimator)
    }
    val textWatcherAnimator = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0.isNullOrBlank().not() && readyToAnimate){
                goUpViews()
                readyToAnimate = false
            }else if (p0.isNullOrBlank()){
                goDownViews()
                readyToAnimate = true
            }
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    }

    private fun goUpViews(){

        val searchGoUpAnimator : ObjectAnimator = ObjectAnimator.ofFloat( SearchAnyEditText, ViewAnimator.TRANSLATION_Y , -400.0f )
        searchGoUpAnimator.delayUntilDone(SearchAnyEditText)
        searchGoUpAnimator.duration = 700

        val recViewFadeAnim : ObjectAnimator = ObjectAnimator.ofFloat( RecViewLinearLayout, ViewAnimator.ALPHA , 0.0f )
        recViewFadeAnim.duration = 500

        recViewFadeAnim.start()
        searchGoUpAnimator.start()
    }
    private fun goDownViews(){

        val searchGoUpAnimator : ObjectAnimator = ObjectAnimator.ofFloat( SearchAnyEditText, ViewAnimator.TRANSLATION_Y , 0.0f )
        searchGoUpAnimator.delayUntilDone(SearchAnyEditText)
        searchGoUpAnimator.duration = 500

        val recViewFadeAnim : ObjectAnimator = ObjectAnimator.ofFloat( RecViewLinearLayout, ViewAnimator.ALPHA , 1.0f )
        recViewFadeAnim.duration = 700

        recViewFadeAnim.start()
        searchGoUpAnimator.start()
    }

    private fun ObjectAnimator.delayUntilDone(view : View){
        addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }
            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }


}