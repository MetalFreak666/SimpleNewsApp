package com.example.simplenewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplenewsapp.R
import com.example.simplenewsapp.data.models.Article
import com.example.simplenewsapp.ui.NewsActivity
import com.example.simplenewsapp.ui.adapters.NewsPageAdapter
import com.example.simplenewsapp.ui.adapters.SearchNewsAdapter
import com.example.simplenewsapp.utils.Resource
import com.example.simplenewsapp.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Fragment used to searching news by user
 */
class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var searchNewsAdapter: SearchNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).newsViewModel

        //Setup of RecyclerView for articles
        setupRecyclerView()

        //Setting up Coroutine for delay of searching query
        var job: Job? = null
        search_news_search_view.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                job?.cancel()
                //Setting up delay when text is changed in SearchView
                job = MainScope().launch {
                    delay(1000L)
                    viewModel.searchNews(newText.toString())
                }
                return false
            }
        })

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { news ->
                        submitNews(news.articles)
                    }
                }
                is Resource.Loading -> {
                    Timber.i("Loading articles...")
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
        searchNewsAdapter.setOnClickListener { article ->
            val bundle = Bundle().apply { putSerializable("article", article) }

            //Using navigation controller to navigate and passing bundle to new fragment
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleDetailFragment, bundle)
        }
    }

    //Function used to setup of RecyclerView
    private fun setupRecyclerView() {
        searchNewsAdapter = SearchNewsAdapter()
        search_news_recycler_view.apply {
            adapter = searchNewsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    //Providing RecyclerView Adapter with List of articles from fetched news
    private fun submitNews(articles: List<Article>) {
        searchNewsAdapter.submitArticles(articles)
    }
}