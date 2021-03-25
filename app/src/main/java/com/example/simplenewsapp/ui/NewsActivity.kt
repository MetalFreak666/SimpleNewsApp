package com.example.simplenewsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.simplenewsapp.R
import com.example.simplenewsapp.database.ArticleDB
import com.example.simplenewsapp.repository.NewsRepository
import com.example.simplenewsapp.viewmodels.NewsViewModel
import com.example.simplenewsapp.viewmodels.NewsViewModelProvider
import kotlinx.android.synthetic.main.activity_news.*
import timber.log.Timber

/**
 * News activity used to display fragments and navigation menu
 */
class NewsActivity : AppCompatActivity() {
    lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        navigation_menu.setupWithNavController(newsNavHostFragment.findNavController())

        //Setup of TimberLogger
        Timber.plant(Timber.DebugTree())

        //Init of repository and view model
        val repository = NewsRepository(ArticleDB(this))
        val viewModelProvider = NewsViewModelProvider(repository)
        newsViewModel = ViewModelProvider(this, viewModelProvider).get(NewsViewModel::class.java)
    }
}