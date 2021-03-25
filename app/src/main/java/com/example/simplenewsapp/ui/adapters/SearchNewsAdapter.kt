package com.example.simplenewsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenewsapp.R
import com.example.simplenewsapp.data.models.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_item_article.view.*
import timber.log.Timber

/**
 * Adapter class for RecyclerView in SearchNewsFragment
 * Displaying articles that are searched based on user input
 */
class SearchNewsAdapter() : RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder>() {

    //Articles to display in RecyclerView
    private var searchedArticles: List<Article> = ArrayList()

    class SearchNewsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val articlePublished: TextView = view.article_published_date
        private val articleSource: TextView = view.article_source
        private val articleTitle: TextView = view.article_title
        private val articleDescription: TextView = view.article_description
        private val articleImage: ImageView = view.article_image

        fun bind(article: Article) {

            //Using Picasso to loading images from provided ImageURL for given article
            try {
                Picasso.with(view.context).load(article.urlToImage).into(articleImage)
            } catch (e: Exception) {
                Timber.w("Error while loading image from URL : ${e.localizedMessage}")
            }

            //Filling up given article information
            articlePublished.text = article.publishedAt
            articleSource.text = article.source.name
            articleTitle.text = article.title
            articleDescription.text = article.description
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchNewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellFromRow = layoutInflater.inflate(R.layout.recycler_item_article, parent, false)

        return SearchNewsViewHolder(cellFromRow)
    }

    override fun onBindViewHolder(holder: SearchNewsViewHolder, position: Int) {
        val article = searchedArticles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return searchedArticles.size
    }

    //Submitting articles from NewsFragment
    fun submitArticles(articles: List<Article>) {
        searchedArticles = articles

        //Notifying that data has been changed
        notifyDataSetChanged()
    }
}