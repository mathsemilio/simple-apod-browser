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

package br.com.mathsemilio.simpleapodbrowser.ui.common.manager

import android.content.Context
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.ui.common.util.*

class MessagesManager(private val context: Context) {

    fun showCheckYourConnectionMessage() {
        context.showLongToast(context.getString(R.string.message_check_your_connection_and_try_again))
    }

    fun showUnexpectedErrorOccurredMessage() {
        context.showLongToast(context.getString(R.string.message_unexpected_error_occurred))
    }

    fun showApodAddedToFavoritesMessage() {
        context.showLongToast(context.getString(R.string.message_apod_added_to_favorites_completed))
    }

    fun showApodImageExportedMessage() {
        context.showShortToast(context.getString(R.string.message_apod_image_exported_successfully))
    }

    fun showImageCacheClearedMessage() {
        context.showShortToast(context.getString(R.string.message_cached_images_removed_successfully))
    }
}
