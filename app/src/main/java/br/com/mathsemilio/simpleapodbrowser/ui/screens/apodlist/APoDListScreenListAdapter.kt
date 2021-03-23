package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.APoDListItemView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.APoDListItemViewImpl

class APoDListScreenListAdapter(
    private val layoutInflater: LayoutInflater,
    private val listener: Listener
) : ListAdapter<APoD, APoDListScreenListAdapter.ViewHolder>(APoDListScreenDiffUtilCallback()),
    APoDListItemView.Listener {

    interface Listener {
        fun onAPoDClicked(apod: APoD)
    }

    class ViewHolder(listItemView: APoDListItemViewImpl) :
        RecyclerView.ViewHolder(listItemView.rootView) {
        val apodListItemView = listItemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val apodListItemView = APoDListItemViewImpl(layoutInflater, parent)
        apodListItemView.addListener(this)
        return ViewHolder(apodListItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apodListItemView.bindAPoDDetails(getItem(position))
    }

    override fun onAPoDClicked(apod: APoD) {
        listener.onAPoDClicked(apod)
    }
}