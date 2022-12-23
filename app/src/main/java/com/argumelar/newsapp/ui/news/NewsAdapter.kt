package com.argumelar.newsapp.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.argumelar.newsapp.databinding.AdapterHeadlinesBinding
import com.argumelar.newsapp.databinding.AdapterNewsBinding
import com.argumelar.newsapp.source.news.ArticleModel
import com.argumelar.newsapp.util.DateUtil

val HEADLINES = 1
val NEWS = 2

class NewsAdapter(
    val articles: ArrayList<ArticleModel>,
    val listener: OnAdapterListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        var VIEW_TYPES = HEADLINES
    }

    class ViewHolderNews(val binding: AdapterNewsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(article: ArticleModel){
            binding.article = article
            binding.format = DateUtil()
        }
    }

    class ViewHolderHeadlines(val binding: AdapterHeadlinesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(article: ArticleModel){
            binding.article = article
            binding.format = DateUtil()
        }
    }

    interface OnAdapterListener{
        fun onClick(article: ArticleModel){

        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPES

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (VIEW_TYPES == HEADLINES){
            ViewHolderHeadlines(
                AdapterHeadlinesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else ViewHolderNews(
            AdapterNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = articles[position]
//        holder.binding.tvTitle.text = article.title
//        holder.binding.tvPublished.text = article.publishedAt
        if (VIEW_TYPES == HEADLINES) (holder as ViewHolderHeadlines).bind(article)
        else (holder as ViewHolderNews).bind(article)
//        holder.bind(article)
        holder.itemView.setOnClickListener {
            listener.onClick(article)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun addArticle(data: List<ArticleModel>){
        articles.addAll(data)
        notifyItemRangeInserted( (articles.size - data.size), data.size )
    }

    fun clear(){
        articles.clear()
        notifyDataSetChanged()
    }
}