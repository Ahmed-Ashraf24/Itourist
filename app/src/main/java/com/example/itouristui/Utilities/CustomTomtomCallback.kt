package com.example.itouristui.Utilities

import com.tomtom.sdk.search.SearchCallback
import com.tomtom.sdk.search.SearchResponse
import com.tomtom.sdk.search.common.error.SearchError

class CustomTomtomCallback(val tomtomListener : (SearchResponse)->(Unit)) : SearchCallback {
    override fun onError(error: SearchError) {
        println(error.message)
    }

    override fun onSuccess(result: SearchResponse) {
        tomtomListener(result)
    }
}