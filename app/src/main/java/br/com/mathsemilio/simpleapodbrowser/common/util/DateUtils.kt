/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.simpleapodbrowser.common.util

import android.content.Context
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.*
import java.text.SimpleDateFormat
import java.util.*

fun getDaysIn(dayRange: Int): String {
    val calendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -dayRange) }
    return calendar.timeInMillis.formatTimeInMillis()
}

fun convertDefaultDateRangeFrom(rangeFromPreferences: String): Int {
    return when (rangeFromPreferences) {
        DEFAULT_DATE_RANGE_LAST_SEVEN_DAYS -> 6
        DEFAULT_DATE_RANGE_LAST_FOURTEEN_DAYS -> 13
        DEFAULT_DATE_RANGE_LAST_TWENTY_ONE_DAYS -> 20
        DEFAULT_DATE_RANGE_LAST_THIRTY_DAYS -> 29
        else -> throw IllegalArgumentException(ILLEGAL_DEFAULT_DATE_RANGE_EXCEPTION)
    }
}

fun Long.formatTimeInMillis(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(this)
}

fun String.formatDate(context: Context): String {
    val year = this.substring(0..3)
    val month = this.substring(5..6)
    val day = this.substring(8..9)
    val formattedDate = "${convertMonthNumberToString(month)} $day, $year"

    return context.getString(R.string.date, formattedDate)
}

fun convertMonthNumberToString(month: String): String {
    return when (month) {
        "01" -> "January"
        "02" -> "February"
        "03" -> "March"
        "04" -> "April"
        "05" -> "May"
        "06" -> "June"
        "07" -> "July"
        "08" -> "August"
        "09" -> "September"
        "10" -> "October"
        "11" -> "November"
        "12" -> "December"
        else -> throw IllegalArgumentException(INVALID_MONTH_EXCEPTION)
    }
}
