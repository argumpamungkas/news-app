package com.argumelar.newsapp.source.news

import com.argumelar.newsapp.BuildConfig
import com.argumelar.newsapp.source.network.ApiClient
import org.koin.dsl.module

val repositoryModule = module {
    factory { NewsRepository(get(), get()) } // menggunakan factory karena datanya dinamis atau berubbah
}

class NewsRepository(
    private val api: ApiClient,
    val db: NewsDao
) {

    suspend fun fetchApi(
        category: String,
        query: String,
        page: Int,
    ): NewsModel {
        return api.fetchNews(
            BuildConfig.API_KEY,
            "id",
            category,
            query,
            page,
        )
    }

    suspend fun find(articleModel: ArticleModel) = db.find(articleModel.publishedAt) // menemukan data

    suspend fun save(articleModel: ArticleModel) {
        db.save(articleModel)
    } // simpan data

    suspend fun remove(articleModel: ArticleModel) {
        db.remove(articleModel)
    } // hapus data
}