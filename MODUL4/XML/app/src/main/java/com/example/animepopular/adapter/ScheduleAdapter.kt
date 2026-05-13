package com.example.animepopular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animepopular.databinding.ItemScheduleBinding
import com.example.animepopular.model.ScheduleItem

class ScheduleAdapter(
    private val isIndonesian: Boolean
) : ListAdapter<ScheduleItem, ScheduleAdapter.ScheduleViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<ScheduleItem>() {
        override fun areItemsTheSame(oldItem: ScheduleItem, newItem: ScheduleItem) =
            oldItem.DateEn == newItem.DateEn
        override fun areContentsTheSame(oldItem: ScheduleItem, newItem: ScheduleItem) =
            oldItem == newItem
    }

    inner class ScheduleViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScheduleItem) {
            binding.tvScheduleDay.text     = if (isIndonesian) item.DateIn    else item.DateEn
            binding.tvScheduleAniname.text = if (isIndonesian) item.AninameIn else item.AninameEn
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemScheduleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}