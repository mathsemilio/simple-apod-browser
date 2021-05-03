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

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.ViewFactory
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.APoDListItemView

class APoDListScreenListAdapter(
    private val viewFactory: ViewFactory,
    private val listener: Listener
) : ListAdapter<APoD, APoDListScreenListAdapter.ViewHolder>(APoDListScreenDiffUtilCallback()),
    APoDListItemView.Listener {

    interface Listener {
        fun onAPoDClicked(apod: APoD)
    }

    class ViewHolder(private val listItemView: APoDListItemView) :
        RecyclerView.ViewHolder(listItemView.rootView) {

        fun bind(apod: APoD) = listItemView.bindAPoDDetails(apod)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(viewFactory.getAPoDListItemView(parent).also { listItemView ->
            listItemView.addListener(this)
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onAPoDClicked(apod: APoD) {
        listener.onAPoDClicked(apod)
    }
}