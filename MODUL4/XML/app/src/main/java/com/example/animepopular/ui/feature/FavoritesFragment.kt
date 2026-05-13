package com.example.animepopular.ui.feature

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animepopular.adapter.AnimeAdapter
import com.example.animepopular.data.AnimeRepository
import com.example.animepopular.data.FavoritesRepository
import com.example.animepopular.databinding.FragmentFavoritesBinding
import com.example.animepopular.model.AnimeItem

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AnimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        refreshList()
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun setupAdapter() {
        adapter = AnimeAdapter(
            onImdbClick = { anime -> openUrl(anime.imdbUrl) },
            onDetailClick = { anime -> navigateToDetail(anime) },
            onFavoriteToggled = { _, _ -> refreshList() }
        )
        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorites.adapter = adapter
    }

    private fun refreshList() {
        val favoriteIds = FavoritesRepository.getFavoriteIds()
        val favorites: List<AnimeItem> = if (favoriteIds.isEmpty()) {
            emptyList()
        } else {
            AnimeRepository.getAnimeList().filter { it.id in favoriteIds }
        }

        adapter.submitList(favorites)

        if (favorites.isEmpty()) {
            binding.tvFavoritesCount.text = "0 Anime"
            binding.tvEmptyFavorites.visibility = View.VISIBLE
        } else {
            binding.tvFavoritesCount.text = "${favorites.size} Anime"
            binding.tvEmptyFavorites.visibility = View.GONE
        }
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun navigateToDetail(anime: AnimeItem) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(anime)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}