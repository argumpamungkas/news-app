package com.argumelar.newsapp.source.news

import androidx.lifecycle.LiveData
import androidx.room.*
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query


// DATA ACCESS OBJECT (kalau diretrofit ini adalah endpoint nya)
@Dao
interface NewsDao {

    @Query("SELECT * FROM tableArticle")
    fun findAll(): LiveData<List<ArticleModel>> // ini untuk mendapatkan semua data

    @Query("SELECT COUNT(*) FROM tableArticle WHERE publishedAt=:publish")
    suspend fun find(publish: String): Int // untuk membaca setiap detail dari berita apakah sudah ditambahkan ke bookmark atau tidak (menemukan data)

    @Insert(onConflict = OnConflictStrategy.REPLACE) // agar data tidak double saat disave
    suspend fun save(articleModel: ArticleModel) // save data

    @Delete
    suspend fun remove(articleModel: ArticleModel) // delete data

}