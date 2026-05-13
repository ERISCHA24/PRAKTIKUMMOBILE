package com.example.animepopular.ui.feature

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animepopular.adapter.AnimeAdapter
import com.example.animepopular.data.AnimeRepository
import com.example.animepopular.databinding.FragmentGenreBinding
import com.example.animepopular.model.AnimeItem

class GenreFragment : Fragment() {

    private var _binding: FragmentGenreBinding? = null
    private val binding get() = _binding!!
    private val args: GenreFragmentArgs by navArgs()
    private lateinit var adapter: AnimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val genre = args.genre
        val filtered = AnimeRepository.getAnimeList().filter {
            it.genreEn.contains(genre, ignoreCase = true)
        }

        binding.tvGenreTitle.text = "Genre: $genre"
        binding.tvGenreCount.text = "${filtered.size} anime"

        adapter = AnimeAdapter(
            onImdbClick = { anime -> openUrl(anime.imdbUrl) },
            onDetailClick = { anime -> navigateToDetail(anime) }
        )
        binding.rvGenreAnime.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGenreAnime.adapter = adapter
        adapter.submitList(filtered)

        // Genre chip buttons
        val genres = listOf("Action", "Fantasy", "Mecha", "Comedy", "Sci-Fi", "Drama")
        setupGenreChips(genres)
    }

    private fun setupGenreChips(genres: List<String>) {
        val chips = listOf(
            binding.chip1, binding.chip2, binding.chip3,
            binding.chip4, binding.chip5, binding.chip6
        )
        genres.forEachIndexed { index, genre ->
            if (index < chips.size) {
                chips[index].text = genre
                chips[index].setOnClickListener {
                    val filtered = AnimeRepository.getAnimeList().filter {
                        it.genreEn.contains(genre, ignoreCase = true)
                    }
                    adapter.submitList(filtered)
                    binding.tvGenreTitle.text = "Genre: $genre"
                    binding.tvGenreCount.text = "${filtered.size} anime"
                }
            }
        }
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    fun navigateToDetail(anime: AnimeItem) {
        val action = GenreFragmentDirections.actionGenreFragmentToDetailFragment(anime)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}