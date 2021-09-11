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

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.ARG_DIALOG_MESSAGE
import br.com.mathsemilio.simpleapodbrowser.common.ARG_DIALOG_TITLE
import br.com.mathsemilio.simpleapodbrowser.common.ARG_POSITIVE_BUTTON_TEXT
import br.com.mathsemilio.simpleapodbrowser.ui.dialog.BaseDialogFragment
import com.google.android.material.button.MaterialButton

class InfoDialog : BaseDialogFragment() {

    companion object {
        fun newInstance(
            title: String,
            message: String,
            positiveButtonText: String
        ) = InfoDialog().apply {
            arguments = Bundle(3).apply {
                putString(ARG_DIALOG_TITLE, title)
                putString(ARG_DIALOG_MESSAGE, message)
                putString(ARG_POSITIVE_BUTTON_TEXT, positiveButtonText)
            }
        }
    }

    private val title get() = requireArguments().getString(ARG_DIALOG_TITLE, "")

    private val message get() = requireArguments().getString(ARG_DIALOG_MESSAGE, "")

    private val positiveButtonText get() = requireArguments().getString(ARG_POSITIVE_BUTTON_TEXT, "")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let { activity ->
            val dialogBuilder = AlertDialog.Builder(activity)
            val layoutInflater = requireActivity().layoutInflater

            val dialogView = layoutInflater.inflate(R.layout.layout_info_dialog, null)
            dialogBuilder.setView(dialogView)

            setupDialogViews(dialogView)

            dialogBuilder.create()
        }
    }

    private fun setupDialogViews(dialogView: View?) {
        dialogView?.let { view ->
            val titleTextView = view.findViewById<TextView>(R.id.text_view_info_dialog_title)
            val messageTextView = view.findViewById<TextView>(R.id.text_view_info_dialog_message)
            val positiveButton = view.findViewById<MaterialButton>(R.id.button_info_dialog_positive)

            titleTextView.text = title

            messageTextView.text = message

            positiveButton.apply {
                text = positiveButtonText
                setOnClickListener { dismiss() }
            }
        }
    }
}