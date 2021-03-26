package com.example.simplenewsapp.database

import android.content.Context
import androidx.room.*
import com.example.simplenewsapp.data.models.Article

/**
 * Room database class
 */
@Database(
    entities = [Article::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class ArticleDB : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDAO

    companion object {
        @Volatile
        private var instance: ArticleDB? = null
        private var LOCK = Any()

        //Ensuring that there is only one instance of the database at time
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        //Function for creating database
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ArticleDB::class.java,
            "article_db.db"
        ).build()
    }
}