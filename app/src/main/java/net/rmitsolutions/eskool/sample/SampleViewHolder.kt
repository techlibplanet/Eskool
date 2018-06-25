package net.rmitsolutions.eskool.sample

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import net.rmitsolutions.eskool.R
import net.rmitsolutions.eskool.viewmodels.ProfileViewModel

/**
 * Created by RMIT on 14/10/2017.
 */
class SampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(profileViewModel: ProfileViewModel) {
        val textViewLabel = itemView.findViewById<TextView>(R.id.textLabel) as TextView
        val textViewValue = itemView.findViewById<TextView>(R.id.textValue) as TextView

        textViewLabel.text = profileViewModel.label
        textViewValue.text = profileViewModel.value
    }
}