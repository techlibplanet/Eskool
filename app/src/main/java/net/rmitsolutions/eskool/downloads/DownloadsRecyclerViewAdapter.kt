package net.rmitsolutions.eskool.downloads

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.rmitsolutions.eskool.databinding.DownloadsListDataBinding
import net.rmitsolutions.eskool.helpers.AutoUpdatableAdapter
import net.rmitsolutions.eskool.viewmodels.DownloadsViewModel
import kotlin.properties.Delegates

/**
 * Created by Madhu on 19-Jul-2017.
 */
class DownloadsRecyclerViewAdapter(val listener: OnItemClickListener) : RecyclerView.Adapter<DownloadsViewHolder>(), AutoUpdatableAdapter {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadsViewHolder {
        val dataBinding = DownloadsListDataBinding.inflate(LayoutInflater.from(parent!!.context), parent, false)
        val viewHolder = DownloadsViewHolder(dataBinding)

        viewHolder.dataBinding.ddClickListener = (object : DownloadFileClickListener {
            override fun onDownloadFileOpenClick(view: View) {
                val pos = viewHolder.adapterPosition
                listener.onOpenFileClick(pos)
            }

            override fun onDownloadFileDeleteClick(view: View) {
                val pos = viewHolder.adapterPosition
                listener.onDeleteFileClick(pos)
            }
        })
        return viewHolder
    }

    override fun onBindViewHolder(holder: DownloadsViewHolder, position: Int) {
        val model = items[position]
        holder.dataBinding.viewModel = model
    }

    var items: MutableList<DownloadsViewModel> by Delegates.observable(mutableListOf()) {
        prop, old, new ->
        autoNotify(old, new) { o, n -> o.fileName == n.fileName }
    }

    override fun getItemCount() = items.size


}