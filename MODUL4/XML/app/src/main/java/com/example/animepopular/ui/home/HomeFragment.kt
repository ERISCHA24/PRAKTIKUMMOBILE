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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animepopular.R
import com.example.animepopular.adapter.AnimeAdapter
import com.example.animepopular.adapter.CarouselAdapter
import com.example.animepopular.databinding.FragmentHomeBinding
import com.example.animepopular.model.AnimeItem
import com.example.animepopular.viewmodel.AnimeViewModel
import com.example.animepopular.viewmodel.AnimeViewModelFactory
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AnimeViewModel by viewModels {
        AnimeViewModelFactory("popular")
    }

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

        Timber.d("HomeFragment created with filter: ${viewModel.getFilterParam()}")

        setupMenu()
        setupCarousel()
        setupRecyclerView()
        observeData()
        setupQuickButtons()
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
            onImdbClick = { anime ->
                Timber.d("Carousel: IMDB button clicked for ${anime.titleEn}")
                openUrl(anime.imdbUrl)
            },
            onDetailClick = { anime ->
                Timber.d("Carousel: Detail button clicked for ${anime.titleEn}")
                navigateToDetail(anime)
            }
        )
        binding.viewPagerCarousel.adapter = carouselAdapter
        binding.dotsIndicator.attachTo(binding.viewPagerCarousel)
    }

    private fun setupRecyclerView() {
        animeAdapter = AnimeAdapter(
            onImdbClick = { anime ->
                Timber.d("List: IMDB button clicked for ${anime.titleEn}")
                openUrl(anime.imdbUrl)
            },
            onDetailClick = { anime ->
                Timber.d("List: Detail button clicked for ${anime.titleEn}")
                navigateToDetail(anime)
            }
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
        lifecycleScope.launch {
            viewModel.animeList.collect { list ->
                animeAdapter.submitList(list)
                carouselAdapter.submitList(list)
                Timber.d("Anime list updated with ${list.size} items")
            }
        }

        lifecycleScope.launch {
            viewModel.detailButtonClicked.collect { clicked ->
                if (clicked) {
                    Timber.d("Detail button state changed - navigating to detail")
                    viewModel.resetDetailButtonState()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.explicitIntentClicked.collect { clicked ->
                if (clicked) {
                    Timber.d("Explicit Intent button state changed")
                    viewModel.resetExplicitIntentState()
                }
            }
        }
    }

    private fun setupQuickButtons() {
        binding.btnQuickGenre.setOnClickListener {
            Timber.d("Quick Genre button clicked")
            val action = HomeFragmentDirections.actionHomeFragmentToGenreFragment(genre = "Action")
            findNavController().navigate(action)
        }
        binding.btnQuickNews.setOnClickListener {
            Timber.d("Quick News button clicked")
            findNavController().navigate(R.id.action_homeFragment_to_newsFragment)
        }
        binding.btnQuickSchedule.setOnClickListener {
            Timber.d("Quick Schedule button clicked")
            findNavController().navigate(R.id.action_homeFragment_to_scheduleFragment)
        }
    }

    private fun openUrl(url: String) {
        Timber.d("Opening URL: $url")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun navigateToDetail(anime: AnimeItem) {
        Timber.d("Navigating to detail for: ${anime.titleEn}")
        viewModel.onDetailButtonClicked(anime)
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(anime)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
