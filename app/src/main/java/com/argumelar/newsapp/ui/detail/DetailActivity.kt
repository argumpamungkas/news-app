package com.argumelar.newsapp.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.argumelar.newsapp.R
import com.argumelar.newsapp.R.*
import com.argumelar.newsapp.databinding.ActivityDetailBinding
import com.argumelar.newsapp.databinding.CustomToolbarBinding
import com.argumelar.newsapp.source.news.ArticleModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val detailModule = module {
    factory { DetailActivity() }
}

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private lateinit var bindingToolbar: CustomToolbarBinding
    private val viewModel: DetailViewModel by viewModel()
    private val detailArticle by lazy {
        intent.getSerializableExtra("DetailArticle") as ArticleModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingToolbar = binding.toolbar
        setContentView(binding.root)

        setSupportActionBar(bindingToolbar.containerToolbar)
        supportActionBar!!.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true) // bar kembali
        }

        detailArticle.let {
            viewModel.find(it) // untuk menentukan detailArticle yang di bookmark
            val web = binding.webView
            web.loadUrl(detailArticle.url!!) // meload url article
            web.webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.pbTop.visibility = View.GONE
                }
            }
            val settings = binding.webView.settings
            settings.javaScriptCanOpenWindowsAutomatically = false
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bookmark, menu)
//        membuat variable bookmark
        val menuBookmark = menu!!.findItem(R.id.action_bookmark)
        menuBookmark.setOnMenuItemClickListener {
            viewModel.bookmark(detailArticle) // mengambil detailArticle karena sudah ditetapkan sebagai As ArticleModel

            if (viewModel.isBookmark.value == 0){
                Toast.makeText(applicationContext, "Add Bookmark", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Remove From Bookmark", Toast.LENGTH_SHORT).show()
            }
            true
        }
        viewModel.isBookmark.observe(this) { // menggunakan this bukan viewOwnerLifecycle karena ini adalah activity, karena activity sudah mempunyai context
            if (it == 0) {
                menuBookmark.setIcon(R.drawable.ic_add)
            } else {
                menuBookmark.setIcon(R.drawable.ic_check)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }
}