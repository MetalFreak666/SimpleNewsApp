package com.example.simplenewsapp.repository

import com.example.simplenewsapp.data.api.RetrofitService
import com.example.simplenewsapp.data.models.Article
import com.example.simplenewsapp.database.ArticleDB

/**
 * Repository class used for access remote data from NewsAPI
 */
class NewsRepository(val db: ArticleDB) {

    //Function for getting news
    suspend fun getNews(countryCode: String, pageNumber: Int) =
        RetrofitService.newsAPI.getNews(countryCode, pageNumber)

    //Function for searching news based on query world
    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitService.newsAPI.searchNews(searchQuery, pageNumber)

    //Function used to insert an article into db
    suspend fun insert(article: Article) = db.getArticleDao().insert(article)

    //Function used to getting saved articles
    fun getSavedArticles() = db.getArticleDao().getArticles()

    //Function used to delete articles from db
    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

}