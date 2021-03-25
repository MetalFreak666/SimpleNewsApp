package com.example.simplenewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.simplenewsapp.R
import com.example.simplenewsapp.ui.NewsActivity
import com.example.simplenewsapp.viewmodels.NewsViewModel

/**
 * Fragment used to show saved news in the local database
 */
class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).newsViewModel
    }
}