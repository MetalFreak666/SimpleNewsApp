package com.example.simplenewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.simplenewsapp.R
import com.example.simplenewsapp.ui.NewsActivity
import com.example.simplenewsapp.viewmodels.NewsViewModel
import com.squareup.picasso.Picasso
import timber.log.Timber

/**
 * Fragment used to display selected article by user
 */
class ArticleDetailFragment : Fragment(R.layout.fragment_article_detail) {

    lateinit var viewModel: NewsViewModel
    private val args: ArticleDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).newsViewModel
        val article = args.article

        val source: TextView = view.findViewById(R.id.article_detail_source)
        val publishedDate: TextView = view.findViewById(R.id.article_detail_published_date)
        val title: TextView = view.findViewById(R.id.article_detail_title)
        val description: TextView = view.findViewById(R.id.article_detail_description)
        val image: ImageView = view.findViewById(R.id.article_detail_image_view)

        //Using picasso to load image from URL
        try {
            Picasso.with(view.context).load(article.urlToImage).into(image)
        } catch (e: Exception) {
            Timber.w("Error while loading image from URL : ${e.localizedMessage}")
        }

        source.text = article.source.name
        publishedDate.text = article.publishedAt
        title.text = article.title
        description.text = article.description
    }
}