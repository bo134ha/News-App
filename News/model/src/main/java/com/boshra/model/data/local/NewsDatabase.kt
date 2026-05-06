package com.boshra.model.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class], version = 3, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract val articleDao: ArticleDao
}


object DatabaseClient {
    private var instance: NewsDatabase? = null

    fun getDatabase(context: Context): NewsDatabase {
        return instance ?: synchronized(this) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                NewsDatabase::class.java,
                "news_db"
            ).build()
            instance = db
            db
        }
    }
}