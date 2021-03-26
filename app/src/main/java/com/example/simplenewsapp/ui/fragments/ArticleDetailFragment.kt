package com.example.simplenewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.simplenewsapp.R
import com.example.simplenewsapp.ui.NewsActivity
import com.example.simplenewsapp.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_article_detail.*
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
        val savedArticle = args.savedArticle

        val source: TextView = view.findViewById(R.id.article_detail_source)
        val publishedDate: TextView = view.findViewById(R.id.article_detail_published_date)
        val title: TextView = view.findViewById(R.id.article_detail_title)
        val description: TextView = view.findViewById(R.id.article_detail_description)
        val image: ImageView = view.findViewById(R.id.article_detail_image_view)
        val web: WebView = view.findViewById(R.id.article_detail_web_view)

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

        //FAB used to open URL in WebView
        fab_web.setOnClickListener {
            web.apply {
                webViewClient = WebViewClient()
                loadUrl(article.url)
            }

            //Setting UI components visibility to false once user click on fab web
            source.isVisible = false
            publishedDate.isVisible = false
            title.isVisible = false
            description.isVisible = false
            image.isVisible = false
            fab_web.isVisible = false
        }

        //If saved article display delete FAB
        if (savedArticle) {
            fab_save_article.isVisible = false
            fab_delete_article.isVisible = true

            //Deleting article from DB
            fab_delete_article.setOnClickListener {
                viewModel.deleteArticle(article)
                findNavController().navigate(R.id.action_articleDetailFragment_to_savedNewsFragment2)
            }
        } else {
            //FAB used to save article
            fab_save_article.setOnClickListener{
                viewModel.saveArticle(article)

                //Notification when article is saved
                Snackbar.make(view, R.string.article_saved_successfully, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}