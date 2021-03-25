package com.example.simplenewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.simplenewsapp.R
import com.example.simplenewsapp.ui.NewsActivity
import com.example.simplenewsapp.viewmodels.NewsViewModel
import timber.log.Timber

/**
 * Dialog fragment used to display selected article by user
 */
class ArticleDetailFragment : Fragment(R.layout.fragment_article_detail) {

    lateinit var viewModel: NewsViewModel
    private val args: ArticleDetailFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).newsViewModel

        val article = args.article
        Timber.i("GG + $article")
        //TODO display selected article info in UI
    }
}