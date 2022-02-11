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

package br.com.mathsemilio.simpleapodbrowser.common.util.date

import java.util.*
import java.text.SimpleDateFormat
import br.com.mathsemilio.simpleapodbrowser.common.*

fun Long.formatMillisDate(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(this)
}

fun String.formatDate(): String {
    val year = this.substring(0..3)
    val monthNumber = this.substring(5..6)
    val day = this.substring(8..9).removePrefix("0")

    return "${convertMonthNumberToName(monthNumber)} $day, $year"
}

private fun convertMonthNumberToName(monthNumber: String): String {
    return when (monthNumber) {
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
        else -> throw IllegalArgumentException(INVALID_MONTH_NUMBER)
    }
}
