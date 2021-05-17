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
        textViewPromptDialogTitle = findViewById(R.id.text_view_prompt_dialog_title)
        textViewPromptDialogMessage = findViewById(R.id.text_view_prompt_dialog_message)
        buttonPromptDialogPositive = findViewById(R.id.button_prompt_dialog_positive)
        buttonPromptDialogNegative = findViewById(R.id.button_prompt_dialog_negative)
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
            setOnClickListener { notifyPositiveButtonClick() }
        }
    }

    override fun setNegativeButtonText(negativeButtonText: String?) {
        if (negativeButtonText != null)
            buttonPromptDialogNegative.apply {
                text = negativeButtonText
                setOnClickListener { notifyNegativeButtonClick() }
            }
        else
            buttonPromptDialogNegative.isVisible = false
    }

    private fun notifyPositiveButtonClick() {
        listeners.forEach { listener ->
            listener.onPositiveButtonClicked()
        }
    }

    private fun notifyNegativeButtonClick() {
        listeners.forEach { listener ->
            listener.onNegativeButtonClicked()
        }
    }
}