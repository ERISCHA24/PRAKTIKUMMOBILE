package com.example.animepopular.ui.feature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animepopular.adapter.NewsAdapter
import com.example.animepopular.data.NewsRepository
import com.example.animepopular.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isIndonesian = getCurrentLanguage() == "id"
        val newsList = NewsRepository.getNewsList()

        val adapter = NewsAdapter(isIndonesian)
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.adapter = adapter
        adapter.submitList(newsList)

        binding.tvNewsEmpty.visibility =
            if (newsList.isEmpty()) View.VISIBLE else View.GONE
        binding.rvNews.visibility =
            if (newsList.isEmpty()) View.GONE else View.VISIBLE
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