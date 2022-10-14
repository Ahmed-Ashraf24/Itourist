package com.example.itouristui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.ViewAnimator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main3.*

class MainActivity3 : AppCompatActivity() {

    private var readyToAnimate = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        showCustomUI()


        println("test number 1")
        println("test number 2")
        println("test number 3")
        val popularSearch = listOf<String>("Chicken" , "Pi" , "Burger" , "Mcdonalds" , "Shawrma","Pasta","Crape","Dessert")

        with(SearchRecView){
            layoutManager = FlexboxLayoutManager(this@MainActivity3).apply {
                flexDirection = FlexDirection.ROW

            }
            itemAnimator = DefaultItemAnimator()
            adapter = PopularSearchRecViewAdapter(popularSearch)
        }




       ActualAnySearchEditText.addTextChangedListener(textWatcherAnimator)
    }

    private fun showCustomUI(){
        window.decorView.apply {
            systemUiVisibility =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
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

    val textWatcherAnimator = object:TextWatcher{
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
}