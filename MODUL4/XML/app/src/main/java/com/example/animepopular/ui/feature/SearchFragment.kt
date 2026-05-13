package com.example.animepopular.ui.feature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animepopular.adapter.AnimeAdapter
import com.example.animepopular.data.AnimeRepository
import com.example.animepopular.databinding.FragmentSearchBinding
import com.example.animepopular.model.AnimeItem
import android.content.Intent
import android.net.Uri

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AnimeAdapter
    private val fullList = AnimeRepository.getAnimeList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AnimeAdapter(
            onImdbClick = { anime -> openUrl(anime.imdbUrl) },
            onDetailClick = { anime -> navigateToDetail(anime) }
        )
        binding.rvSearchResults.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchResults.adapter = adapter
        adapter.submitList(fullList)

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim().lowercase()
                val filtered = if (query.isEmpty()) fullList
                else fullList.filter {
                    it.titleEn.lowercase().contains(query) ||
                            it.genreEn.lowercase().contains(query) ||
                            it.studioEn.lowercase().contains(query)
                }
                adapter.submitList(filtered)
                binding.tvNoResults.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun navigateToDetail(anime: AnimeItem) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(anime)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}