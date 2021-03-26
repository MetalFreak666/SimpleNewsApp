package com.example.simplenewsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenewsapp.R
import com.example.simplenewsapp.data.models.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_item_article.view.*
import timber.log.Timber

/**
 * Adapter class for saved news recycler view
 * Improved version of adapter class comparing to other two
 */
class SavedNewsAdapter() : RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>() {

    //Saved articles provided by SavedNewsFragment
    private var savedArticles: List<Article> = ArrayList()

    inner class SavedNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {
        return SavedNewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_article, parent, false))
    }

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        val article = savedArticles[position]

        holder.itemView.apply {

            //Using Picasso to loading images from provided ImageURL for given article
            try {
                Picasso.with(context).load(article.urlToImage).into(article_image)
            } catch (e: Exception) {
                Timber.w("Error while loading image from URL : ${e.localizedMessage}")
            }

            //Using Kotlin extension to call UI elements directly
            article_published_date.text = article.publishedAt
            article_source.text = article.source.name
            article_title.text = article.title
            article_description.text = article.description
        }
    }

    override fun getItemCount(): Int {
        return savedArticles.size
    }

    //Function for submitting articles and notify adapter about change
    fun submitSavedArticles(articles: List<Article>) {
        savedArticles = articles
        notifyDataSetChanged()
    }
}