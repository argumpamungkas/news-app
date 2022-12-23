package com.argumelar.newsapp.source.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.argumelar.newsapp.source.news.ArticleModel
import com.argumelar.newsapp.source.news.NewsDao
import com.argumelar.newsapp.util.SourceConverter

@Database(
    entities = [ArticleModel::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(SourceConverter::class)
abstract class DatabaseClient: RoomDatabase() {
    abstract val newsDao: NewsDao
}