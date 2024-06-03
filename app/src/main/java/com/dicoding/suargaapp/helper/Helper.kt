package com.dicoding.suargaapp.helper

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit


object Helper {
     fun calculateAge(birthday: String): Int {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val birthDate = dateFormat.parse(birthday) ?: return 0
        val birthCalendar = Calendar.getInstance().apply { time = birthDate }
        val today = Calendar.getInstance()

        var age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age
    }

    fun calculatePregnancyAge(pregnancyDate: String): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val startDate = dateFormat.parse(pregnancyDate) ?: return 0
        val startCalendar = Calendar.getInstance().apply { time = startDate }
        val today = Calendar.getInstance()

        val diffInMillis = today.timeInMillis - startCalendar.timeInMillis
        val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)

        return diffInDays / 7
    }

    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val dayOfWeekString = when (dayOfWeek) {
            Calendar.SUNDAY -> "Minggu"
            Calendar.MONDAY -> "Senin"
            Calendar.TUESDAY -> "Selasa"
            Calendar.WEDNESDAY -> "Rabu"
            Calendar.THURSDAY -> "Kamis"
            Calendar.FRIDAY -> "Jumat"
            Calendar.SATURDAY -> "Sabtu"
            else -> ""
        }

        val monthString = when (month) {
            Calendar.JANUARY -> "Januari"
            Calendar.FEBRUARY -> "Februari"
            Calendar.MARCH -> "Maret"
            Calendar.APRIL -> "April"
            Calendar.MAY -> "Mei"
            Calendar.JUNE -> "Juni"
            Calendar.JULY -> "Juli"
            Calendar.AUGUST -> "Agustus"
            Calendar.SEPTEMBER -> "September"
            Calendar.OCTOBER -> "Oktober"
            Calendar.NOVEMBER -> "November"
            Calendar.DECEMBER -> "Desember"
            else -> ""
        }

        return "$dayOfWeekString, $dayOfMonth $monthString $year"
    }
}