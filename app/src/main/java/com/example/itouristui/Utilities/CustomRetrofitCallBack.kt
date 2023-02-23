package com.example.itouristui.Utilities

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomRetrofitCallBack<T : Collection<Any>>(val onSuccessListener : (Response<T>) -> (Unit)) : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful){
            onSuccessListener(response)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        println("Error due to ${t.message}")
    }
}