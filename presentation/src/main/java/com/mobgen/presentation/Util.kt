package com.mobgen.presentation

import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        private const val DAYS_WEEK = 7
        private const val HOUR_DAY = 24
        private const val MINUTE_HOUR = 60
        private const val SECOND_MINUTE = 60
        private const val SECOND_MILLISECOND = 1000
        const val SHORT_FORMAT_DATE = "dd/MM/YY"
        const val LONG_FORMAT_DATE = "dd/MM/YY HH:mm:ss"

        fun getTweetDateFormated(date: Date, format: String): String {
            val simpleDateFormat = SimpleDateFormat(format, Locale.ENGLISH)
            val tweetDate = GregorianCalendar.getInstance(Locale.ENGLISH).apply { time = date }
            val currentDate = GregorianCalendar.getInstance(Locale.ENGLISH)
            val diffInMillies = Math.abs(currentDate.timeInMillis - tweetDate.timeInMillis)
            val diffCalendar = GregorianCalendar.getInstance(Locale.ENGLISH).apply { this.timeInMillis = diffInMillies }

            return if (diffInMillies < (DAYS_WEEK * HOUR_DAY * MINUTE_HOUR * SECOND_MINUTE * SECOND_MILLISECOND)) {
                when {
                    diffCalendar.get(Calendar.DAY_OF_YEAR) > 1 -> "${diffCalendar.get(Calendar.DAY_OF_YEAR) - 1}d"
                    diffCalendar.get(Calendar.HOUR) - 1 > 0 -> "${diffCalendar.get(Calendar.HOUR) - 1}h"
                    diffCalendar.get(Calendar.MINUTE) > 0 -> "${diffCalendar.get(Calendar.MINUTE)}m"
                    else -> "${diffCalendar.get(Calendar.SECOND)}s"
                }
            } else {
                simpleDateFormat.format(date)
            }
        }

        fun getTwetDateSimple(date: Date, format: String) = SimpleDateFormat(format, Locale.ENGLISH).format(date)
    }
}