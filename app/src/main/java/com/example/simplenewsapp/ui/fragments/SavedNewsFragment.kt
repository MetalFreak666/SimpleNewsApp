package com.example.simplenewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplenewsapp.R
import com.example.simplenewsapp.data.models.Article
import com.example.simplenewsapp.ui.NewsActivity
import com.example.simplenewsapp.ui.adapters.NewsPageAdapter
import com.example.simplenewsapp.ui.adapters.SavedNewsAdapter
import com.example.simplenewsapp.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_saved_news.*

/**
 * Fragment used to show saved news in the local database
 */
class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var savedNewsAdapter: SavedNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).newsViewModel

        //Setup of RecyclerView for articles
        setupRecyclerView()

        viewModel.getSavedArticles().observe(viewLifecycleOwner, Observer { savedArticles ->
            savedNewsAdapter.submitSavedArticles(savedArticles)
        })

        //When user selecting an article from RecyclerView
        savedNewsAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
                putSerializable("savedArticle", true)
            }

            //Using navigation controller to parsing article data to the ArticleDetailFragment
            findNavController().navigate(R.id.action_savedNewsFragment_to_articleDetailFragment, bundle)
        }
    }

    //Function used to setup of adapter
    private fun setupRecyclerView() {
        savedNewsAdapter = SavedNewsAdapter()
        saved_news_recycler.apply {
            adapter = savedNewsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}