package com.example.simplenewsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenewsapp.data.models.News
import com.example.simplenewsapp.repository.NewsRepository
import com.example.simplenewsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * NewsViewModel class created based on:
 * https://developer.android.com/jetpack/guide
 */
class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    val news: MutableLiveData<Resource<News>> = MutableLiveData()
    val searchNews: MutableLiveData<Resource<News>> = MutableLiveData()
    var country = "us"
    var pages = 1

    init {
        getNews(country, pages)
    }

    //Used to fetch data(news)
    private fun getNews(countryCode: String, pages: Int) = viewModelScope.launch {
        news.postValue(Resource.Loading())
        val response = repository.getNews(countryCode, pages)
        news.postValue(responseHandler(response))
    }

    //Used to fetch data(news) based on search query
    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = repository.searchNews(searchQuery, pages)
        searchNews.postValue(searchNewsResponseHandler(response))
    }

    private fun responseHandler(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {
            response.body()?.let { results ->
                return Resource.Success(results)
            }
        }
        return Resource.Error(response.message())
    }

    private fun searchNewsResponseHandler(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {
            response.body()?.let { results ->
                return Resource.Success(results)
            }
        }
        return Resource.Error(response.message())
    }
}