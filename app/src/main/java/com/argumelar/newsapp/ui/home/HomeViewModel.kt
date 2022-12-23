package com.argumelar.newsapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argumelar.newsapp.source.news.CategoryModel
import com.argumelar.newsapp.source.news.NewsModel
import com.argumelar.newsapp.source.news.NewsRepository
import kotlinx.coroutines.launch
import org.koin.dsl.module
import kotlin.math.ceil

val homeViewModel = module {
    factory { HomeViewModel(get()) }
}

class HomeViewModel(
    val repository: NewsRepository
) : ViewModel() {

    val title = "Berita"

    val category by lazy { MutableLiveData<String>() }
    val message by lazy { MutableLiveData<String>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    val loadMore by lazy { MutableLiveData<Boolean>() } // loading untuk ke page berikutnya
    val news by lazy { MutableLiveData<NewsModel>() }

//    dijalankan saat viewmodel ini dijalankan
    init {
        category.value = "" // nilai id "", data pertama categorynya adalah kosong atau berita utama
        message.value = null // untuk error bernilai null
    }

    var query = ""
    var page = 1
    var total = 1 // total maksimal halaman

    fun fetch() {
        if (page > 1) {
            loadMore.value = true
        } else {
            loading.value = true
        }
        viewModelScope.launch {
            try {
                val response = repository.fetchApi(
                    category.value!!,
                    query,
                    page
                )
                news.value = response
//                untuk menghitung total
                val totalResult: Double = response.totalResults / 20.0
                total = ceil(totalResult).toInt() // untuk mendapatkan data (total/20)
                page++
                loading.value = false
                loadMore.value = false
            } catch (e: Exception) { // jika error terjadi
                message.value = "Terjadi Kesalahan"
            }
        }
    }

    val categories = listOf<CategoryModel>(
        CategoryModel("", "Berita Utama"),
        CategoryModel("business", "Bisnis"),
        CategoryModel("entertainment", "Hiburan"),
        CategoryModel("general", "Umum"),
        CategoryModel("health", "Kesehatan"),
        CategoryModel("science", "Sains"),
        CategoryModel("sports", "Olahraga"),
        CategoryModel("technology", "Teknologi"),
    )

}