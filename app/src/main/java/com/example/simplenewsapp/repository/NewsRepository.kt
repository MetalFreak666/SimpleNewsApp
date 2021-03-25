package com.example.simplenewsapp.repository

import com.example.simplenewsapp.data.api.RetrofitService

/**
 * Repository class used for access remote data from NewsAPI
 */
class NewsRepository {

    //Function for getting news
    suspend fun getNews(countryCode: String, pageNumber: Int) =
        RetrofitService.newsAPI.getNews(countryCode, pageNumber)

    //Function for searching news based on query world
    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitService.newsAPI.searchNews(searchQuery, pageNumber)
}