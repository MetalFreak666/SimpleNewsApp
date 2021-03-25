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
 * Adapter class for RecyclerView in NewsFragment
 * Displaying articles fetched from remote
 */
class NewsPageAdapter() : RecyclerView.Adapter<NewsPageAdapter.NewsPageViewHolder>() {

    //Articles to display in RecyclerView
    private var articlesToDisplay: List<Article> = ArrayList()
    private var onItemClickListener: ((Article) -> Unit)? = null

    class NewsPageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val articlePublished: TextView = view.article_published_date
        private val articleSource: TextView = view.article_source
        private val articleTitle: TextView = view.article_title
        private val articleDescription: TextView = view.article_description
        private val articleImage: ImageView = view.article_image

        fun bind(article: Article, onClick: (Article) -> Unit) {

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

            //OnClickListener when selecting an article from RecyclerView
            view.setOnClickListener {
                onClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsPageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellFromRow = layoutInflater.inflate(R.layout.recycler_item_article, parent, false)

        return NewsPageViewHolder(cellFromRow)
    }

    override fun onBindViewHolder(holder: NewsPageViewHolder, position: Int) {
        val article = articlesToDisplay[position]
        onItemClickListener?.let { holder.bind(article, it) }
    }

    override fun getItemCount(): Int {
        return articlesToDisplay.size
    }

    //Submitting articles from NewsFragment
    fun submitArticles(articles: List<Article>) {
        articlesToDisplay = articles

        //Notifying that data has been changed
        notifyDataSetChanged()
    }

    //Called when user selecting an article
    fun setOnClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}