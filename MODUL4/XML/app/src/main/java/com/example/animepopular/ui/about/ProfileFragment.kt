package com.example.animepopular.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.animepopular.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvUsername.text = "AnimeLovers_ID"
        binding.tvEmail.text = "animelover@email.com"
        binding.tvWatched.text = "5"
        binding.tvFavorites.text = "5"
        binding.tvReviews.text = "3"

        binding.btnSettings.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToSettingsFragment()
            findNavController().navigate(action)
        }

        binding.btnAbout.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToAboutFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}