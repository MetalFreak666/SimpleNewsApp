package com.example.simplenewsapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.simplenewsapp.data.models.Article

/**
 * Data Access Object class where interaction with database is defined
 */
@Dao
interface ArticleDAO {

    //Insert function
    //When article exist it will be replaced using OnConflictStrategy
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article) : Long

    //Getting articles from db
    @Query("SELECT * FROM articles")
    fun getArticles() : LiveData<List<Article>>

    //Deleting article from db
    @Delete
    suspend fun deleteArticle(article: Article)
}