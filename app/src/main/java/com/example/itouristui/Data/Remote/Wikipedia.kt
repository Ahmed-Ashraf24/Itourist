package com.example.itouristui.Data.Remote

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private val BASE_URL = "https://en.wikipedia.org/"

private val retrofitWiki = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL).build()


interface WikiAPIInterface{
    @GET("w/api.php?action=query&format=json&prop=extracts&formatversion=2&exsentences=9&exlimit=1&origin=*&explaintext=1")
    fun getCityDataByName(@Query("titles") name : String):Call<String>

    @GET("w/api.php?action=opensearch&format=json&prop=extracts&formatversion=2&exsentences=9&exlimit=1&origin=*&explaintext=1")
    fun getClosestResult(@Query("search") name : String) : Call<String>

    @GET("w/api.php?action=query&format=json&formatversion=2&prop=pageimages%7Cpageterms&piprop=thumbnail&pithumbsize=600&origin=*")
    fun getCityImage(@Query("titles") name : String):Call<String>

}

object WikipediaData{
    val wikiApiImp : WikiAPIInterface by lazy{
        retrofitWiki.create(WikiAPIInterface::class.java)
    }
}
