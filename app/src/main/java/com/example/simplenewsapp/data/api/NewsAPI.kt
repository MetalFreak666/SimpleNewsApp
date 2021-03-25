package com.example.simplenewsapp.data.api

import com.example.simplenewsapp.data.api.Constants.Companion.API_KEY
import com.example.simplenewsapp.data.models.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface used to getting news from NewsAPI
 * Documentation: https://newsapi.org/docs
 */
interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<News>

}