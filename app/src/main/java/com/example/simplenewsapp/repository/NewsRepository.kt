package com.example.simplenewsapp.repository

import com.example.simplenewsapp.data.api.RetrofitService

/**
 * Repository class used for access remote data from NewsAPI
 */
class NewsRepository {

    //Function for getting news
    suspend fun getNews(countryCode: String, pageNumber: Int) =
        RetrofitService.newsAPI.getNews(countryCode, pageNumber)
}