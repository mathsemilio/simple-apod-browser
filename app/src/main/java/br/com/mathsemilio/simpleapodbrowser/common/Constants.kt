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

package br.com.mathsemilio.simpleapodbrowser.common

const val BASE_URL = "https://api.nasa.gov/planetary/"

const val FAVORITE_APOD_TABLE = "favorite_apod_table"
const val FAVORITE_APOD_DATABASE = "favorite_apod_database"

const val CACHED_APOD_TABLE = "CACHED_APOD_TABLE"

const val ARG_APOD = "ARG_APOD"
const val ARG_APOD_IMAGE = "ARG_APOD_IMAGE"
const val OUT_STATE_APOD = "OUT_STATE_APOD"
const val OUT_STATE_APOD_IMAGE = "OUT_STATE_APOD_IMAGE"

const val ARG_DIALOG_TITLE = "ARG_DIALOG_TITLE"
const val ARG_DIALOG_MESSAGE = "ARG_DIALOG_MESSAGE"
const val ARG_POSITIVE_BUTTON_TEXT = "ARG_DIALOG_POSITIVE_BUTTON_TEXT"
const val ARG_NEGATIVE_BUTTON_TEXT = "ARG_DIALOG_NEGATIVE_BUTTON_TEXT"
const val ARG_IS_CANCELABLE = "ARG_IS_CANCELABLE"

const val APOD_TYPE_IMAGE = "image"
const val APOD_TYPE_VIDEO = "video"

const val DEFAULT_DATE_RANGE_PREFERENCE_KEY = "DEFAULT_DATE_RANGE_PREFERENCE"
const val CLEAR_IMAGE_CACHE_PREFERENCE_KEY = "CLEAR_IMAGE_CACHE_PREFERENCE"
const val APP_VERSION_PREFERENCE_KEY = "APP_VERSION_PREFERENCE"

const val DEFAULT_DATE_RANGE_LAST_SEVEN_DAYS = "0"
const val DEFAULT_DATE_RANGE_LAST_FOURTEEN_DAYS = "1"
const val DEFAULT_DATE_RANGE_LAST_TWENTY_ONE_DAYS = "2"
const val DEFAULT_DATE_RANGE_LAST_THIRTY_DAYS = "3"

const val RC_WRITE_EXTERNAL_STORAGE = 1000

const val FIRST_APOD_DATE_IN_MILLIS = 803271600000L

const val INVALID_MONTH = "Invalid month. Must be in the 1 to 12 month range"
const val ILLEGAL_DEFAULT_DATE_RANGE = "Illegal default date range"