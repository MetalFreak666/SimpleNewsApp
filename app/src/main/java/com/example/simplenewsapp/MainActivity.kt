package com.example.simplenewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simplenewsapp.repository.NewsRepository
import com.example.simplenewsapp.utils.Resource
import com.example.simplenewsapp.viewmodels.NewsViewModel
import com.example.simplenewsapp.viewmodels.NewsViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setup of TimberLogger
        Timber.plant(Timber.DebugTree())

        //Init of repository and view model
        val repository = NewsRepository()
        val viewModelProvider = NewsViewModelProvider(repository)
        newsViewModel = ViewModelProvider(this, viewModelProvider).get(NewsViewModel::class.java)

        newsViewModel.news.observe(this, {response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { news ->
                        //Test
                        hello_world_tv.text = news.toString()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { errorMessage ->
                        Timber.d("Error: $errorMessage")
                    }
                }
                is Resource.Loading -> {
                    Timber.i("Loading articles...")
                }
            }
        })
    }
}