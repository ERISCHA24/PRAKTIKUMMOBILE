package com.example.animepopular.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.animepopular.R
import com.example.animepopular.adapter.ReviewAdapter
import com.example.animepopular.data.FavoritesRepository
import com.example.animepopular.databinding.FragmentDetailBinding
import com.example.animepopular.model.AnimeItem
import com.example.animepopular.model.Review
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val anime = args.anime
        val isIndonesian = getCurrentLanguage() == "id"

        bindData(anime, isIndonesian)
        setupWatchedButton(anime)
        setupReviewSection(anime)
    }

    private fun bindData(anime: AnimeItem, isIndonesian: Boolean) {
        Glide.with(this)
            .load(anime.imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_gallery)
            .centerCrop()
            .into(binding.imgDetailPoster)

        binding.tvDetailTitle.text    = if (isIndonesian) anime.titleId   else anime.titleEn
        binding.tvDetailYear.text     = anime.year.toString()
        binding.tvDetailGenre.text    = if (isIndonesian) anime.genreId   else anime.genreEn
        binding.tvDetailStudio.text   = if (isIndonesian) anime.studioId  else anime.studioEn
        binding.tvDetailEpisodes.text = anime.episodes
        binding.tvDetailRating.text   = anime.rating
        binding.tvDetailPlot.text     = if (isIndonesian) anime.plotId    else anime.plotEn

        binding.btnDetailImdb.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(anime.imdbUrl)))
        }
    }

    private fun setupWatchedButton(anime: AnimeItem) {
        updateWatchedButtonState(anime.id)

        binding.btnWatched.setOnClickListener {
            FavoritesRepository.toggleWatched(anime.id)
            updateWatchedButtonState(anime.id)
        }
    }

    private fun updateWatchedButtonState(animeId: Int) {
        val isWatched = FavoritesRepository.isWatched(animeId)
        if (isWatched) {
            binding.btnWatched.setIconResource(R.drawable.ic_watched)
            binding.btnWatched.iconTint =
                ContextCompat.getColorStateList(requireContext(), R.color.accent_color)
            binding.btnWatched.strokeColor =
                ContextCompat.getColorStateList(requireContext(), R.color.accent_color)
        } else {
            binding.btnWatched.setIconResource(R.drawable.ic_watched_border)
            binding.btnWatched.iconTint =
                ContextCompat.getColorStateList(requireContext(), R.color.text_secondary)
            binding.btnWatched.strokeColor =
                ContextCompat.getColorStateList(requireContext(), R.color.text_secondary)
        }
    }

    private fun setupReviewSection(anime: AnimeItem) {
        reviewAdapter = ReviewAdapter()
        binding.rvReviews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvReviews.adapter = reviewAdapter
        binding.rvReviews.isNestedScrollingEnabled = false

        refreshReviews(anime.id)

        binding.btnSendReview.setOnClickListener {
            submitReview(anime.id)
        }
    }

    private fun submitReview(animeId: Int) {
        val username = binding.etReviewUsername.text.toString().trim()
        val reviewText = binding.etReviewText.text.toString().trim()

        if (username.isEmpty()) {
            binding.etReviewUsername.error = "X"
            binding.etReviewUsername.requestFocus()
            return
        }
        if (reviewText.isEmpty()) {
            binding.etReviewText.error = "X"
            binding.etReviewText.requestFocus()
            return
        }

        val timestamp = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            .format(Date())
        val review = Review(
            username = username,
            text = reviewText,
            timestamp = timestamp
        )

        FavoritesRepository.addReview(animeId, review)
        refreshReviews(animeId)

        binding.etReviewUsername.text?.clear()
        binding.etReviewText.text?.clear()
        binding.etReviewUsername.clearFocus()
        binding.etReviewText.clearFocus()
    }

    private fun refreshReviews(animeId: Int) {
        val reviews = FavoritesRepository.getReviews(animeId)
        reviewAdapter.submitList(reviews)

        binding.tvReviewCount.text = when (reviews.size) {
            1    -> "1 review"
            else -> "${reviews.size} review"
        }
    }

    private fun getCurrentLanguage(): String {
        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        return prefs.getString("language", "en") ?: "en"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}