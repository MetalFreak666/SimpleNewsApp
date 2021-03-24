package com.example.simplenewsapp.data.models

/**
 * Data model class for News entity
 */
data class News (
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
    )
