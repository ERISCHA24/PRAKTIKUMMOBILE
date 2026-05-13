package com.example.animepopular.data

import com.example.animepopular.model.ScheduleItem

object ScheduleRepository {
    fun getScheduleList(): List<ScheduleItem> = listOf(
        ScheduleItem(
            DateEn = "Monday",
            DateIn = "Senin",
            AninameEn = "• Attack on Titan (22:00)\n• Spy x Family (20:00)",
            AninameIn = "• Serangan Titan (22:00)\n• Spy x Family (20:00)",
        ),
        ScheduleItem(
            DateEn = "Tuesday",
            DateIn = "Selasa",
            AninameEn = "• Demon Slayer (22:30)\n• Hunter x Hunter (21:00)",
            AninameIn = "• Pembasmi Iblis (22:30)\n• Hunter x Hunter (21:00)",
        ),
        ScheduleItem(
            DateEn = "Wednesday",
            DateIn = "Rabu",
            AninameEn = "• Gurren Lagann (20:00)",
            AninameIn = "• Gurren Lagann (20:00)",
        ),
        ScheduleItem(
            DateEn = "Thursday",
            DateIn = "Kamis",
            AninameEn = "• Code Geass (21:00)\n• Trigun (23:00)",
            AninameIn = "• Code Geass (21:00)\n• Trigun (23:00)",
        ),
        ScheduleItem(
            DateEn = "Friday",
            DateIn = "Jumat",
            AninameEn = "• Demon Slayer (22:00)\n• Spy x Family (20:00)",
            AninameIn = "• Pembasmi Iblis (22:00)\n• Spy x Family (20:00)",
        ),
        ScheduleItem(
            DateEn = "Saturday",
            DateIn = "Sabtu",
            AninameEn = "• Attack on Titan (23:00)\n• Hunter x Hunter (21:00)",
            AninameIn = "• Serangan Titan (23:00)\n• Hunter x Hunter (21:00)",
        ),
        ScheduleItem(
            DateEn = "Sunday",
            DateIn = "Minggu",
            AninameEn = "• Code Geass (20:00)\n• Gurren Lagann (21:30)\n• Trigun (23:00)",
            AninameIn = "• Code Geass (20:00)\n• Gurren Lagann (21:30)\n• Trigun (23:00)",
        ),
    )
}