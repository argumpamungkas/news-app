package com.argumelar.newsapp.source.news

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class NewsModel(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleModel>
)

@Entity (tableName = "tableArticle")
data class ArticleModel(
    val source: SourceModel?, // ini bertipe data class dan bukan tipe data maka harus di convert
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    @PrimaryKey (autoGenerate = false) // primary key false, karena akan menggunakan publishedAt
    val publishedAt: String,
    val content: String?,
) : Serializable

data class SourceModel(
    val id: String?,
    val name: String?
) : Serializable // ditambahkan karena variable source yang ada pada data class articlemodel mengambil dari data class lain
