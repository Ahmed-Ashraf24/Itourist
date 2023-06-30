package com.example.itouristui.Data.Remote


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private val BASE_URL = "https://api.unsplash.com/"

private val retrofitUnsplash = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL).build()


interface UnsplashInterface{
    @GET("search/photos?orientation=landscape&client_id=3n9DVXLbpuPfLf8cft7krUvGnX5M5fKqGNrevkMidHs")
    fun getCityImages(@Query("query") name : String):Call<String>

}

object UnsplashData{
    val unsplashInterface : UnsplashInterface by lazy{
        retrofitUnsplash.create(UnsplashInterface::class.java)
    }
}
