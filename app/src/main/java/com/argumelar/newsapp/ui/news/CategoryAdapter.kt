package com.argumelar.newsapp.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.argumelar.newsapp.R
import com.argumelar.newsapp.databinding.AdapterCategoryBinding
import com.argumelar.newsapp.source.news.CategoryModel

class CategoryAdapter(
    private val categories: List<CategoryModel>, private val listener: OnAdapterListener
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val items = arrayListOf<TextView>()

    class ViewHolder(val binding: AdapterCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener {
        fun onClick(category: CategoryModel) {

        }
    }

//    return viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdapterCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

//    fungsi untuk mereload
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.category.text = category.name
        items.add(holder.binding.category) // untuk menambah data
        holder.itemView.setOnClickListener {
            listener.onClick(category)
            setColor(holder.binding.category)
        }
    setColor(items[0]) // set awal pada berita utama
    }

    override fun getItemCount(): Int {
        return categories.size
    }

//    Untuk category yang diklik akan berubah warna
    private fun setColor(textView: TextView) {
        items.forEach { it.setBackgroundResource(R.color.white) } // set semua items menjadi putih
        textView.setBackgroundResource(R.color.gray) //text yang sedang kita klik
    }

}