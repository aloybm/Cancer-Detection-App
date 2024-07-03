package com.dicoding.asclepius.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.response.ArticlesItem
import com.dicoding.asclepius.data.response.HealthResponse
import com.dicoding.asclepius.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    private val _listNews = MutableLiveData<List<ArticlesItem>>()
    val listNews: LiveData<List<ArticlesItem>> = _listNews

    companion object {
        private const val TAG = "NewsViewModel"
    }

    fun getListNews() {
        val client = ApiConfig.getApiService().getAllNews()
        client.enqueue(object : Callback<HealthResponse> {
            override fun onResponse(
                call: Call<HealthResponse>, response: Response<HealthResponse>,
            ) {

                if (response.isSuccessful) {
                    val filteredArticles =
                        response.body()?.articles?.filterNot { it.description == "[Removed]" }
                    _listNews.value = filteredArticles!!
                    Log.d(TAG, "List: $filteredArticles")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<HealthResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}