package com.example.animepopular.ui.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.animepopular.MainActivity
import com.example.animepopular.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val currentLang = prefs.getString("language", "en") ?: "en"

        if (currentLang == "id") {
            binding.rgLanguage.check(binding.rbIndonesian.id)
        } else {
            binding.rgLanguage.check(binding.rbEnglish.id)
        }

        binding.btnSaveSettings.setOnClickListener {
            val selectedLang = if (binding.rbIndonesian.isChecked) "id" else "en"
            prefs.edit().putString("language", selectedLang).apply()
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}