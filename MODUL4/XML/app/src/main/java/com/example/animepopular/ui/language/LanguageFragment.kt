package com.example.animepopular.ui.language

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.animepopular.MainActivity
import com.example.animepopular.databinding.FragmentLanguageBinding

class LanguageFragment : Fragment() {

    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val currentLang = prefs.getString("language", "en") ?: "en"

        if (currentLang == "en") {
            binding.btnEnglish.isSelected = true
            binding.cardEnglish.strokeWidth = 4
            binding.cardIndonesian.strokeWidth = 0
        } else {
            binding.btnIndonesian.isSelected = true
            binding.cardIndonesian.strokeWidth = 4
            binding.cardEnglish.strokeWidth = 0
        }

        binding.btnEnglish.setOnClickListener {
            setLanguage("en")
        }

        binding.btnIndonesian.setOnClickListener {
            setLanguage("id")
        }

        binding.cardEnglish.setOnClickListener {
            setLanguage("en")
        }

        binding.cardIndonesian.setOnClickListener {
            setLanguage("id")
        }
    }

    private fun setLanguage(lang: String) {
        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs.edit().putString("language", lang).apply()
        
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}