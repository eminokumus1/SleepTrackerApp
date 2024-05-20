package com.eminokumus.sleeptracker.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eminokumus.sleeptracker.database.SleepNight
import com.eminokumus.sleeptracker.databinding.ListItemSleepNightBinding

class SleepNightAdapter :
    ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

        var sleepClickListener: SleepNightListener? = null

    class ViewHolder private constructor(var sleepNightBinding: ListItemSleepNightBinding) :
        RecyclerView.ViewHolder(sleepNightBinding.root) {
        fun bind(item: SleepNight) {
            sleepNightBinding.sleep = item
            sleepNightBinding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    ListItemSleepNightBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolder(binding)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.sleepNightBinding.root.setOnClickListener {
            sleepClickListener?.onClick(item.nightId)
        }



    }
    interface SleepNightListener{
        fun onClick(sleepId: Long)
    }


}

class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }

}





