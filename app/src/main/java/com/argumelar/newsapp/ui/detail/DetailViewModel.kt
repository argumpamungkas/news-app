package com.argumelar.newsapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argumelar.newsapp.source.news.ArticleModel
import com.argumelar.newsapp.source.news.NewsRepository
import kotlinx.coroutines.launch
import org.koin.dsl.module

val detailViewModel = module {
    factory { DetailViewModel(get()) }
}

class DetailViewModel(
    private val repository: NewsRepository
): ViewModel() {

    val isBookmark by lazy { MutableLiveData<Int>(0) } // mengunakan int karena pada endpoint NewsDao fun find mereturn Int

    fun find(articleModel: ArticleModel){
        viewModelScope.launch {
            isBookmark.value = repository.find(articleModel)
        }
    }

    fun bookmark(articleModel: ArticleModel){
        viewModelScope.launch {
            if (isBookmark.value == 0){
                repository.save(articleModel)
            } else {
                repository.remove(articleModel)
            }
            find(articleModel)
        }
    }


}