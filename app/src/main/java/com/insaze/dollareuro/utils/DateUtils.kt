package com.insaze.dollareuro.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        private val pattern = "dd/MM/yyyy"
        val DAY = 1000 * 60 * 60 * 24

        @SuppressLint("SimpleDateFormat")
        fun longToDate(millis: Long): String {
            return SimpleDateFormat(pattern).format(Date(millis))
        }
    }
}