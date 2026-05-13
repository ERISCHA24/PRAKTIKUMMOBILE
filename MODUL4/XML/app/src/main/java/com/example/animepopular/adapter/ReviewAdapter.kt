package com.example.animepopular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animepopular.databinding.ItemReviewBinding
import com.example.animepopular.model.Review

class ReviewAdapter : ListAdapter<Review, ReviewAdapter.ReviewViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ReviewViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            binding.tvReviewUsername.text = "👤 ${review.username}"
            binding.tvReviewText.text = review.text
            binding.tvReviewTimestamp.text = review.timestamp
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review) =
            oldItem.timestamp == newItem.timestamp && oldItem.username == newItem.username
        override fun areContentsTheSame(oldItem: Review, newItem: Review) = oldItem == newItem
    }
}