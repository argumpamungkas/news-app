package com.argumelar.newsapp.source.network

import com.argumelar.newsapp.source.news.NewsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("top-headlines")
    suspend fun fetchNews(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
        @Query("category") category: String, // Category (bisnis, entertainment, olahraga)
        @Query("q") query: String, // Query untuk search
        @Query("page") page: Int /// max page from totalSize = 20 per Page
    ) : NewsModel

}