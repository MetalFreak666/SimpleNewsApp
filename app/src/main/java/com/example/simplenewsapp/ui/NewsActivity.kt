package com.example.simplenewsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.simplenewsapp.R

/**
 * News activity used to display fragments and navigation menu
 */
class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
    }
}