package com.argumelar.newsapp.source.network

import com.argumelar.newsapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// koin, cara melakukan injection kepada beberapa instance atau objek
val networkModule = module {
    single { provideOkhttpClient() } // menggunakan single karena datanya tidak berubah
    single { provideRetrofit( get() ) }
    single { provideNewsApi( get() ) }
}

// Instace untuk interceptor
fun provideOkhttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build()
}

// Instance untuk retrofit
fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

// Instance untuk Interfac (Endpoint)
fun provideNewsApi(retrofit: Retrofit): ApiClient = retrofit.create(ApiClient::class.java)