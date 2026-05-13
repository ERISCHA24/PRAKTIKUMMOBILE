package com.example.animepopular.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animepopular.databinding.ItemCarouselBinding
import com.example.animepopular.model.AnimeItem

class CarouselAdapter(
    private val onImdbClick: (AnimeItem) -> Unit,
    private val onDetailClick: (AnimeItem) -> Unit
) : ListAdapter<AnimeItem, CarouselAdapter.CarouselViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding = ItemCarouselBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CarouselViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CarouselViewHolder(
        private val binding: ItemCarouselBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: AnimeItem) {
            val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val isId = prefs.getString("language", "en") == "id"

            // Row 1: Title | Year
            binding.tvCarouselTitle.text = if (isId) anime.titleId else anime.titleEn
            binding.tvCarouselYear.text = anime.year.toString()

            // Row 2: Genre | Studio
            binding.tvCarouselGenre.text = if (isId) anime.genreId else anime.genreEn
            binding.tvCarouselStudio.text = if (isId) anime.studioId else anime.studioEn

            Glide.with(context)
                .load(anime.imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .centerCrop()
                .into(binding.imgCarouselPoster)

            binding.btnCarouselImdb.setOnClickListener { onImdbClick(anime) }
            binding.btnCarouselDetail.setOnClickListener { onDetailClick(anime) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<AnimeItem>() {
        override fun areItemsTheSame(oldItem: AnimeItem, newItem: AnimeItem) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: AnimeItem, newItem: AnimeItem) = oldItem == newItem
    }
}