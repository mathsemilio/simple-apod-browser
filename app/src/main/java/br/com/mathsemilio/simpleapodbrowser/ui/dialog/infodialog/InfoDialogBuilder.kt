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

package br.com.mathsemilio.simpleapodbrowser.ui.dialog.infodialog

object InfoDialogBuilder {

    private var title = ""
    private var message = ""
    private var positiveButtonText = ""

    fun withTitle(title: String): InfoDialogBuilder {
        this.title = title
        return this
    }

    fun withMessage(message: String): InfoDialogBuilder {
        this.message = message
        return this
    }

    fun withPositiveButtonText(positiveButtonText: String): InfoDialogBuilder {
        this.positiveButtonText = positiveButtonText
        return this
    }

    fun build(): InfoDialog {
        return InfoDialog.newInstance(title, message, positiveButtonText)
    }
}
