package com.dicoding.asclepius.data.retrofit

import com.dicoding.asclepius.data.response.HealthResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("top-headlines?q=cancer&category=health&language=en&apiKey=415c863f9f6546a79f713e0730de072c")
    fun getAllNews(
    ): Call<HealthResponse>
}