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

import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.view.isVisible
import br.com.mathsemilio.simpleapodbrowser.R
import com.google.android.material.button.MaterialButton

class PromptDialogViewImpl(inflater: LayoutInflater) : PromptDialogView() {

    private var textViewPromptDialogTitle: TextView
    private var textViewPromptDialogMessage: TextView
    private var buttonPromptDialogPositive: MaterialButton
    private var buttonPromptDialogNegative: MaterialButton

    init {
        rootView = inflater.inflate(R.layout.layout_prompt_dialog, null, false)

        textViewPromptDialogTitle = rootView.findViewById(R.id.text_view_prompt_dialog_title)
        textViewPromptDialogMessage = rootView.findViewById(R.id.text_view_prompt_dialog_message)
        buttonPromptDialogPositive = rootView.findViewById(R.id.button_prompt_dialog_positive)
        buttonPromptDialogNegative = rootView.findViewById(R.id.button_prompt_dialog_negative)
    }

    override fun setTitle(title: String) {
        textViewPromptDialogTitle.text = title
    }

    override fun setMessage(message: String) {
        textViewPromptDialogMessage.text = message
    }

    override fun setPositiveButtonText(positiveButtonText: String) {
        buttonPromptDialogPositive.apply {
            text = positiveButtonText
            setOnClickListener { notifyListenerOnPositiveButtonClick() }
        }
    }

    override fun setNegativeButtonText(negativeButtonText: String?) {
        if (negativeButtonText != null)
            buttonPromptDialogNegative.apply {
                text = negativeButtonText
                setOnClickListener { notifyListenerOnNegativeButtonClick() }
            }
        else
            buttonPromptDialogNegative.isVisible = false
    }

    private fun notifyListenerOnPositiveButtonClick() {
        notifyListener { listener ->
            listener.onPositiveButtonClicked()
        }
    }

    private fun notifyListenerOnNegativeButtonClick() {
        notifyListener { listener ->
            listener.onNegativeButtonClicked()
        }
    }
}