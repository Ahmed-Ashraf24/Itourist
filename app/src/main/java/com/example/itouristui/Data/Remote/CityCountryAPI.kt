package com.example.itouristui.Data.Remote

import android.content.Context
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
import java.util.Properties

private const val BASE_URL = "https://spott.p.rapidapi.com/"

fun createHttpClient(context: Context): OkHttpClient {
    val apiKey = getApiKey("RAPID_API_KEY", context)

    return OkHttpClient.Builder().addInterceptor { chain ->
        chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", apiKey)
            .addHeader("X-RapidAPI-Host", "spott.p.rapidapi.com")
            .build().run {
                chain.proceed(this)
            }
    }.build()
}

val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

fun createRetrofit(context: Context): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(createHttpClient(context))
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
}

interface CityCountryApiInterface {
    @GET("places/autocomplete")
    fun getCities(
        @Query("limit") limit: Int = 6,
        @Query("skip") skip: Int = 0,
        @Query("type") type: String = "CITY",
        @Query("q") prefixName: String
    ): Call<List<CityDetails>>
}

object CityCountryApiObject {
    lateinit var cityCountryApiInterface: CityCountryApiInterface

    fun initialize(context: Context) {
        val retrofit = createRetrofit(context)
        cityCountryApiInterface = retrofit.create(CityCountryApiInterface::class.java)
    }
}

fun getApiKey(keyName: String, context: Context): String {
    val properties = Properties()
    return try {
        val inputStream = context.assets.open("local.properties")
        properties.load(inputStream)
        properties.getProperty(keyName, "")
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}
