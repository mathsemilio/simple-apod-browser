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
package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import androidx.recyclerview.widget.DiffUtil
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD

class APoDListScreenDiffUtilCallback : DiffUtil.ItemCallback<APoD>() {
    override fun areItemsTheSame(oldItem: APoD, newItem: APoD): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: APoD, newItem: APoD): Boolean {
        return oldItem == newItem
    }
}