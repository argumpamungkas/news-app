package com.argumelar.newsapp.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.argumelar.newsapp.databinding.CustomToolbarBinding
import com.argumelar.newsapp.databinding.FragmentBookmarkBinding
import com.argumelar.newsapp.source.news.ArticleModel
import com.argumelar.newsapp.ui.detail.DetailActivity
import com.argumelar.newsapp.ui.news.NewsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val bookmarkModule = module {
    factory { BookmarkFragment() }
}

class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var bindingToolbar: CustomToolbarBinding
    private val viewModel: BookmarkViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
// Inflate the layout for this fragment
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        bindingToolbar = binding.toolbar
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingToolbar.title = viewModel.title
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        NewsAdapter.VIEW_TYPES = 2
        binding.rvListBookmark.adapter = newsAdapter
        viewModel.articles.observe(viewLifecycleOwner) {
            newsAdapter.clear()
            newsAdapter.addArticle(it)
        }

    }

    private val newsAdapter by lazy {
        NewsAdapter(arrayListOf(), object : NewsAdapter.OnAdapterListener {
            override fun onClick(article: ArticleModel) {
                super.onClick(article)
                startActivity(
                    Intent(requireActivity(), DetailActivity::class.java)
                        .putExtra("DetailArticle", article)
                )
            }
        })
    }


}