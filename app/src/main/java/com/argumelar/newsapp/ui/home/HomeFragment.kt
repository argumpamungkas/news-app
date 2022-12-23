package com.argumelar.newsapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import com.argumelar.newsapp.R
import com.argumelar.newsapp.databinding.CustomToolbarBinding
import com.argumelar.newsapp.databinding.FragmentHomeBinding
import com.argumelar.newsapp.source.news.ArticleModel
import com.argumelar.newsapp.source.news.CategoryModel
import com.argumelar.newsapp.ui.detail.DetailActivity
import com.argumelar.newsapp.ui.news.CategoryAdapter
import com.argumelar.newsapp.ui.news.NewsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module
import timber.log.Timber

val homeModule = module {
    factory { HomeFragment() }
}

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var bindingToolbar: CustomToolbarBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        bindingToolbar = binding.toolbar
        return binding.root
    }

//    digunakan untuk layout yang ada pada fragment ini, maka dgunakan disini
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        bindingToolbar.title = viewModel.title

//    MENAMBAHKAN SEARCH BAR
        bindingToolbar.containerToolbar.inflateMenu(R.menu.menu_search)
        val menu = binding.toolbar.containerToolbar.menu
        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                firstLoad()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.query = it
                }
                return true
            }

        })


//        Timber.i(viewModel.categories.toString())

//    adapter untuk memunculkan list kategori
        binding.rvListItem.adapter = categoryAdapter

//    perubahan data secara langsung dibagian view atau fragment
        viewModel.category.observe(viewLifecycleOwner) {
            Timber.i(it) // it disini adalah category id
//            viewModel.fetch()
            NewsAdapter.VIEW_TYPES = if (it!!.isEmpty()) 1 else 2 // jika id kosong maka melakukan 1 else 2
            firstLoad()
        }

// memunculkan data news
        binding.listNews.adapter = newsAdapter
        viewModel.news.observe(viewLifecycleOwner) {
            Timber.i(it.articles.toString())
            if (viewModel.page == 1) newsAdapter.clear()
            newsAdapter.addArticle(it.articles) // add data news
        }

//    Untuk memunculkan ERROR
        viewModel.message.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

    binding.scrollView.setOnScrollChangeListener {
            v : NestedScrollView, _, scrollY, _, _ ->
        if (scrollY == v.getChildAt(0)!!.measuredHeight - v.measuredHeight) { // fungsi saat scroll, sudah mentok dibawah
            if (viewModel.page <= viewModel.total && viewModel.loadMore.value == false) {
                viewModel.fetch()
            }
        }
    }

    }

    private fun firstLoad(){ // fungsinya untuk perpindahan kategori dimulai dari awal
        binding.scrollView.scrollTo(0,0)
        viewModel.page = 1
        viewModel.total = 1
        viewModel.fetch()
    }

    private val newsAdapter by lazy {
        NewsAdapter(arrayListOf(), object : NewsAdapter.OnAdapterListener {
            override fun onClick(article: ArticleModel) {
                super.onClick(article)
                startActivity(Intent(requireActivity(), DetailActivity::class.java)
                    .putExtra("DetailArticle", article)
                )
            }
        })
    }

    private val categoryAdapter by lazy {
        CategoryAdapter(viewModel.categories, object : CategoryAdapter.OnAdapterListener {
            override fun onClick(category: CategoryModel) {
                super.onClick(category)
//                Timber.i(category.id) untuk cek data
                viewModel.category.postValue(category.id) // menggunakan postValue agar nilainya berubah
            }
        })
    }
}