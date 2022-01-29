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
import br.com.mathsemilio.simpleapodbrowser.common.*

fun getDateRangeFrom(preferenceOption: String): Int {
    return when (preferenceOption) {
        DEFAULT_DATE_RANGE_LAST_SEVEN_DAYS -> 7
        DEFAULT_DATE_RANGE_LAST_FOURTEEN_DAYS -> 14
        DEFAULT_DATE_RANGE_LAST_TWENTY_ONE_DAYS -> 21
        DEFAULT_DATE_RANGE_LAST_THIRTY_DAYS -> 31
        else -> throw IllegalArgumentException(UNKNOWN_DEFAULT_DATE_RANGE)
    }
}

fun Int.formatRange(): String {
    val calendar = Calendar.getInstance()

    calendar.add(Calendar.DAY_OF_YEAR, - this)

    return calendar.timeInMillis.formatMillisDate()
}
