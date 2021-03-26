package com.example.simplenewsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenewsapp.data.models.Article
import com.example.simplenewsapp.data.models.News
import com.example.simplenewsapp.repository.NewsRepository
import com.example.simplenewsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber

/**
 * NewsViewModel class created based on:
 * https://developer.android.com/jetpack/guide
 */
class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    //MutableLiveData
    val news: MutableLiveData<Resource<News>> = MutableLiveData()
    val searchNews: MutableLiveData<Resource<News>> = MutableLiveData()

    var country = "us"
    var pages = 1

    init {
        getNews(country, pages)
    }

    //Used to fetch data(news)
    fun getNews(countryCode: String, pages: Int) = viewModelScope.launch {
        Timber.i("GG $country")
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

    //Response handler function for fetching news
    private fun responseHandler(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {
            response.body()?.let { results ->
                return Resource.Success(results)
            }
        }
        return Resource.Error(response.message())
    }

    //Response handler function for fetching news based on search query
    private fun searchNewsResponseHandler(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {
            response.body()?.let { results ->
                return Resource.Success(results)
            }
        }
        return Resource.Error(response.message())
    }

    //Function used to save article in DB
    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.insert(article)
    }

    //Function used to get saved articles from DB
    fun getSavedArticles() = repository.getSavedArticles()

    //Function used to delete an article from DB
    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }
}