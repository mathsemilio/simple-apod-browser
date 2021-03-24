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

    class ViewHolder(private val itemViewImpl: APoDListItemViewImpl) :
        RecyclerView.ViewHolder(itemViewImpl.rootView) {

        fun bind(apod: APoD) = itemViewImpl.bindAPoDDetails(apod)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(APoDListItemViewImpl(layoutInflater, parent).also { listItemView ->
            listItemView.addListener(this)
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { item ->
            if (item != null) holder.bind(item)
        }
    }

    override fun onAPoDClicked(apod: APoD) {
        listener.onAPoDClicked(apod)
    }
}