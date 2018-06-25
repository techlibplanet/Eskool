package net.rmitsolutions.eskool.sample

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.rmitsolutions.eskool.R
import net.rmitsolutions.eskool.viewmodels.ProfileViewModel

/**
 * Created by RMIT on 14/10/2017.
 */
class SampleRecyclerViewAdapter : RecyclerView.Adapter<SampleViewHolder>() {

    lateinit var  context : Context
    var items: List<ProfileViewModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        context = parent!!.context
        val v = LayoutInflater.from(context).inflate(R.layout.sample_list, parent, false)
        return SampleViewHolder(v)
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder!!.bindView(items[position])
    }




    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return items.size
    }
}