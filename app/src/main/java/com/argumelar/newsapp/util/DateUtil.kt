package com.argumelar.newsapp.util

import java.text.SimpleDateFormat
import java.util.Locale

class DateUtil {
    fun dateFormat(date: String?): String{
        return if (date.isNullOrEmpty()) ""
        else {
            val currentFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()) // format awal
            val dateParse = currentFormat.parse(date) // diparse
            val toFormat = SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault()) // input yg dimunculkan
            toFormat.format(dateParse!!)
        }
    }
}