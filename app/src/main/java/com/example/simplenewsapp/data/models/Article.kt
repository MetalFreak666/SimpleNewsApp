package com.example.simplenewsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Data model class for Article entity
 */
@Entity(tableName = "articles")

//Room will auto generate ID for Article entity
data class Article (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
): Serializable

