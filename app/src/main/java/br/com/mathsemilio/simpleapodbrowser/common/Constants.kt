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
const val APOD_ENDPOINT = "apod"

const val FAVORITE_APOD_TABLE = "favorite_apod_table"
const val FAVORITE_APOD_DATABASE = "favorite_apod_database"

const val ARG_APOD = "ARG_APOD"
const val ARG_APOD_IMAGE = "ARG_APOD_IMAGE"

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

const val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1000

const val FIRST_APOD_DATE_MILLIS = 803271600000L

const val NULL_IMAGE_URI = "Image Uri cannot be null"
const val UNKNOWN_APOD_TYPE = "Invalid APOD type: must be either \"image\" or \"video\""
const val INVALID_MONTH_NUMBER = "Invalid month number. It must be inside the 01 to 12 range"
const val UNKNOWN_DEFAULT_DATE_RANGE = "Unknown default date range"
const val INVALID_PROMPT_DIALOG_EVENT = "Invalid prompt dialog event for this controller"
const val COULD_NOT_OPEN_OUTPUT_STREAM = "Could not open file output stream"
