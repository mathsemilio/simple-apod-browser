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

package br.com.mathsemilio.simpleapodbrowser.ui.dialog.promptdialog

object PromptDialogBuilder {

    private var title = ""
    private var message = ""
    private var positiveButtonText = ""
    private var negativeButtonText: String? = null
    private var isCancelable = false

    fun withTitle(title: String): PromptDialogBuilder {
        this.title = title
        return this
    }

    fun withMessage(message: String): PromptDialogBuilder {
        this.message = message
        return this
    }

    fun withPositiveButtonText(positiveButtonText: String): PromptDialogBuilder {
        this.positiveButtonText = positiveButtonText
        return this
    }

    fun withNegativeButtonText(negativeButtonText: String?): PromptDialogBuilder {
        this.negativeButtonText = negativeButtonText
        return this
    }

    fun setIsCancelable(isCancelable: Boolean): PromptDialogBuilder {
        this.isCancelable = isCancelable
        return this
    }

    fun build(): PromptDialog {
        return PromptDialog.newInstance(
            title,
            message,
            positiveButtonText,
            negativeButtonText,
            isCancelable
        )
    }
}
