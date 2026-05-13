package com.example.animepopular.ui.feature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animepopular.adapter.ScheduleAdapter
import com.example.animepopular.data.ScheduleRepository
import com.example.animepopular.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isIndonesian = getCurrentLanguage() == "id"
        val scheduleList = ScheduleRepository.getScheduleList()

        val adapter = ScheduleAdapter(isIndonesian)
        binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSchedule.adapter = adapter
        adapter.submitList(scheduleList)

        binding.tvScheduleEmpty.visibility =
            if (scheduleList.isEmpty()) View.VISIBLE else View.GONE
        binding.rvSchedule.visibility =
            if (scheduleList.isEmpty()) View.GONE else View.VISIBLE
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