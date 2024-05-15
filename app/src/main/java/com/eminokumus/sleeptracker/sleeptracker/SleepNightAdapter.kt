package com.eminokumus.sleeptracker.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eminokumus.sleeptracker.R
import com.eminokumus.sleeptracker.convertDurationToFormatted
import com.eminokumus.sleeptracker.convertNumericQualityToString
import com.eminokumus.sleeptracker.database.SleepNight
import com.eminokumus.sleeptracker.databinding.ListItemSleepNightBinding

class SleepNightAdapter :
    ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {


    class ViewHolder private constructor(var sleepNightBinding: ListItemSleepNightBinding) :
        RecyclerView.ViewHolder(sleepNightBinding.root) {
        fun bind(item: SleepNight) {
            val res = itemView.context.resources
            sleepNightBinding.run {
                sleepLength.text =
                    convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
                qualityString.text =
                    convertNumericQualityToString(item.sleepQuality, res)
                qualityImage.setImageResource(
                    when (item.sleepQuality) {
                        0 -> R.drawable.ic_sleep_0
                        1 -> R.drawable.ic_sleep_1
                        2 -> R.drawable.ic_sleep_2
                        3 -> R.drawable.ic_sleep_3
                        4 -> R.drawable.ic_sleep_4
                        5 -> R.drawable.ic_sleep_5
                        else -> R.drawable.ic_sleep_active
                    }
                )
            }
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

    class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
        override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem.nightId == newItem.nightId
        }

        override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

    }


}