package com.example.simplenewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplenewsapp.R
import com.example.simplenewsapp.data.models.Article
import com.example.simplenewsapp.ui.NewsActivity
import com.example.simplenewsapp.ui.adapters.NewsPageAdapter
import com.example.simplenewsapp.utils.Resource
import com.example.simplenewsapp.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news.*
import timber.log.Timber

/**
 * Fragment used to showing breaking news from NewsAPI
 */
class   NewsFragment : Fragment(R.layout.fragment_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsPageAdapter: NewsPageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).newsViewModel

        //Setup of RecyclerView for articles
        setupRecyclerView()

        viewModel.news.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { news ->
                        submitNews(news.articles)
                        news_fragment_loading_bar.isVisible = false
                    }
                }
                is Resource.Loading -> {
                    Timber.i("Loading articles...")
                    news_fragment_loading_bar.isVisible = true
                }
                is Resource.Error -> {
                    response.message?.let { errorMessage ->
                        Timber.d("Error: $errorMessage")
                    }
                }
            }
        })

        //This listener is used to navigate to ArticleDetailFragment with bundle
        //which includes selected article as serializable
        newsPageAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            //Using navigation controller to navigate and passing bundle to new fragment
            findNavController().navigate(R.id.action_newsFragment_to_articleDetailFragment, bundle)
        }
    }

    //Function used to setup of RecyclerView
    private fun setupRecyclerView() {
        newsPageAdapter = NewsPageAdapter()
        news_recycler_view.apply {
            adapter = newsPageAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    //Providing RecyclerView Adapter with List of articles from fetched news
    private fun submitNews(articles: List<Article>) {
        newsPageAdapter.submitArticles(articles)
    }
}