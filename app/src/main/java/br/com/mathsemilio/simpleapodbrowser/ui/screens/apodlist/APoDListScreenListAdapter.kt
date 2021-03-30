package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.ViewFactory
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.APoDListItemView

class
APoDListScreenListAdapter(
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