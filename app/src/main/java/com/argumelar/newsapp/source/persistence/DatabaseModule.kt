package com.argumelar.newsapp.source.persistence

import android.app.Application
import androidx.room.Room
import com.argumelar.newsapp.source.news.NewsDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidApplication()) } // tidak get, karena constructornya application
    single { provideNews(get()) }
}

fun provideDatabase(application: Application): DatabaseClient {
    return Room.databaseBuilder(
        application,
        DatabaseClient::class.java,
        "argumNews.db"
    ).fallbackToDestructiveMigration() // menghapus data yg lama dan direplace dengan data yg baru
        .build()
}

fun provideNews(databaseClient: DatabaseClient): NewsDao {
    return databaseClient.newsDao
}