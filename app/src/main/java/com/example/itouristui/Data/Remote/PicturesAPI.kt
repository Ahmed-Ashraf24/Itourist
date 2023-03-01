package com.example.itouristui.Data.Remote



import com.example.itouristui.models.CityDetails
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URLEncoder

private val BASE_URL = "http://192.168.1.6:5000/"




val retrofit2 = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface PicturesApiInterface {
    @GET("pictures")
    fun getCities(@Query("q") query : String): Call<String>
}

object PicturesAPI{
    val pictureApiInterface : PicturesApiInterface by lazy{
        retrofit2.create(PicturesApiInterface::class.java)
    }
}