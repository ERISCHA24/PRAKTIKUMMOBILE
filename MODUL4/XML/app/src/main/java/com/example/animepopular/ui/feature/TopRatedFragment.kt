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
import com.example.animepopular.databinding.FragmentTopRatedBinding
import com.example.animepopular.model.AnimeItem

class TopRatedFragment : Fragment() {

    private var _binding: FragmentTopRatedBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AnimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sorted = AnimeRepository.getAnimeList().sortedByDescending {
            it.rating.replace("★ ", "").replace(" / 10", "").toFloatOrNull() ?: 0f
        }

        adapter = AnimeAdapter(
            onImdbClick = { anime -> openUrl(anime.imdbUrl) },
            onDetailClick = { anime -> navigateToDetail(anime) }
        )
        binding.rvTopRated.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTopRated.adapter = adapter
        adapter.submitList(sorted)
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun navigateToDetail(anime: AnimeItem) {
        val action = TopRatedFragmentDirections.actionTopRatedFragmentToDetailFragment(anime)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}