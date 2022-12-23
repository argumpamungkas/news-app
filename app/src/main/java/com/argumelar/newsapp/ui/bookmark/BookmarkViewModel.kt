package com.argumelar.newsapp.ui.bookmark

import androidx.lifecycle.ViewModel
import com.argumelar.newsapp.source.news.NewsRepository
import org.koin.dsl.module

val bookmarkViewModel = module {
    factory { BookmarkViewModel(get()) }
}

class BookmarkViewModel(
    val repository: NewsRepository
): ViewModel() {

    val title = "Disimpan"
    val articles = repository.db.findAll()

}