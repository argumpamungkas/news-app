package com.argumelar.newsapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.argumelar.newsapp.source.network.networkModule
import com.argumelar.newsapp.source.news.repositoryModule
import com.argumelar.newsapp.source.persistence.databaseModule
import com.argumelar.newsapp.ui.bookmark.bookmarkModule
import com.argumelar.newsapp.ui.bookmark.bookmarkViewModel
import com.argumelar.newsapp.ui.detail.detailModule
import com.argumelar.newsapp.ui.detail.detailViewModel
import com.argumelar.newsapp.ui.home.homeModule
import com.argumelar.newsapp.ui.home.homeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

//Base Class
class NewsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant( Timber.DebugTree() )
        Timber.i("run base application") // untuk debug
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // darkmode nonaktif
        startKoin {
            androidLogger() // untuk cek error dibagian logcat
            androidContext(this@NewsApp)
            modules(
                networkModule,
                repositoryModule,
                homeViewModel,
                homeModule,
                bookmarkViewModel,
                bookmarkModule,
                databaseModule,
                detailViewModel,
                detailModule
            )
        }
    }
}