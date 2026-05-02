package com.example.animepopular.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animepopular.R
import com.example.animepopular.adapter.AnimeAdapter
import com.example.animepopular.adapter.CarouselAdapter
import com.example.animepopular.databinding.FragmentHomeBinding
import com.example.animepopular.model.AnimeItem
import com.example.animepopular.viewmodel.AnimeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AnimeViewModel by viewModels()

    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var carouselAdapter: CarouselAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMenu()
        setupCarousel()
        setupRecyclerView()
        observeData()
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_language -> {
                        findNavController().navigate(R.id.action_homeFragment_to_languageFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupCarousel() {
        carouselAdapter = CarouselAdapter(
            onImdbClick = { anime -> openUrl(anime.imdbUrl) },
            onDetailClick = { anime -> navigateToDetail(anime) }
        )
        binding.viewPagerCarousel.adapter = carouselAdapter

        // Attach dots indicator
        binding.dotsIndicator.attachTo(binding.viewPagerCarousel)
    }

    private fun setupRecyclerView() {
        animeAdapter = AnimeAdapter(
            onImdbClick = { anime -> openUrl(anime.imdbUrl) },
            onDetailClick = { anime -> navigateToDetail(anime) }
        )

        val layoutManager = if (resources.configuration.orientation ==
            android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(requireContext(), 2)
        } else {
            LinearLayoutManager(requireContext())
        }
        binding.rvAnime.layoutManager = layoutManager
        binding.rvAnime.adapter = animeAdapter
    }

    private fun observeData() {
        viewModel.animeList.observe(viewLifecycleOwner) { list ->
            animeAdapter.submitList(list)
            carouselAdapter.submitList(list)
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun navigateToDetail(anime: AnimeItem) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(anime)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}