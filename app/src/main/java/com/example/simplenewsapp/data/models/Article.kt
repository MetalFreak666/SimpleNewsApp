package com.example.simplenewsapp.data.models

/**
 * Data model class for Article entity
 */
data class Article (
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)

