package com.example.animepopular.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.animepopular.databinding.FragmentDetailBinding
import com.example.animepopular.model.AnimeItem

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val anime = DetailFragmentArgs.fromBundle(requireArguments()).anime
        val isIndonesian = getCurrentLanguage() == "id"
        bindData(anime, isIndonesian)
    }

    private fun getCurrentLanguage(): String {
        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        return prefs.getString("language", "en") ?: "en"
    }

    private fun bindData(anime: AnimeItem, isIndonesian: Boolean) {
        Glide.with(this)
            .load(anime.imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_gallery)
            .centerCrop()
            .into(binding.imgDetailPoster)

        binding.tvDetailTitle.text = if (isIndonesian) anime.titleId else anime.titleEn
        binding.tvDetailYear.text = anime.year.toString()
        binding.tvDetailGenre.text = if (isIndonesian) anime.genreId else anime.genreEn
        binding.tvDetailStudio.text = if (isIndonesian) anime.studioId else anime.studioEn
        binding.tvDetailEpisodes.text = anime.episodes
        binding.tvDetailRating.text = anime.rating
        binding.tvDetailPlot.text = if (isIndonesian) anime.plotId else anime.plotEn

        binding.btnDetailImdb.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(anime.imdbUrl))
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}