package com.example.animepopular.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animepopular.databinding.ItemAnimeBinding
import com.example.animepopular.model.AnimeItem

class AnimeAdapter(
    private val onImdbClick: (AnimeItem) -> Unit,
    private val onDetailClick: (AnimeItem) -> Unit
) : ListAdapter<AnimeItem, AnimeAdapter.AnimeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ItemAnimeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AnimeViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AnimeViewHolder(
        private val binding: ItemAnimeBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: AnimeItem) {
            val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val isId = prefs.getString("language", "en") == "id"

            binding.tvTitle.text = if (isId) anime.titleId else anime.titleEn
            binding.tvYear.text = anime.year.toString()
            binding.tvGenre.text = if (isId) anime.genreId else anime.genreEn
            binding.tvPlot.text = if (isId) anime.plotId else anime.plotEn
            binding.tvRating.text = anime.rating
            binding.tvEpisodes.text = anime.episodes

            Glide.with(context)
                .load(anime.imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .into(binding.imgPoster)

            binding.btnImdb.setOnClickListener { onImdbClick(anime) }
            binding.btnDetail.setOnClickListener { onDetailClick(anime) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<AnimeItem>() {
        override fun areItemsTheSame(oldItem: AnimeItem, newItem: AnimeItem) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: AnimeItem, newItem: AnimeItem) = oldItem == newItem
    }
}