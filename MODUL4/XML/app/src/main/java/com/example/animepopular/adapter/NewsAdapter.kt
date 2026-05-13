package com.example.animepopular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animepopular.databinding.ItemNewsBinding
import com.example.animepopular.model.NewsItem

class NewsAdapter(
    private val isIndonesian: Boolean
) : ListAdapter<NewsItem, NewsAdapter.NewsViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<NewsItem>() {
        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem) =
            oldItem.NewsEn == newItem.NewsEn
        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem) =
            oldItem == newItem
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NewsItem) {
            binding.tvNewsTitle.text   = if (isIndonesian) item.NewsIn    else item.NewsEn
            binding.tvNewsContent.text = if (isIndonesian) item.ContentIn else item.ContentEn
            binding.tvNewsUpdate.text  = "🕐 " + if (isIndonesian) item.UpdateIn else item.UpdateEn
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}