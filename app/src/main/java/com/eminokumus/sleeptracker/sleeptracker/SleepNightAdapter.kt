package com.eminokumus.sleeptracker.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eminokumus.sleeptracker.R
import com.eminokumus.sleeptracker.convertDurationToFormatted
import com.eminokumus.sleeptracker.convertNumericQualityToString
import com.eminokumus.sleeptracker.database.SleepNight
import com.eminokumus.sleeptracker.databinding.ListItemSleepNightBinding

class SleepNightAdapter : RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {
    var data = listOf<SleepNight>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder private constructor (var sleepNightBinding: ListItemSleepNightBinding) :
        RecyclerView.ViewHolder(sleepNightBinding.root) {
        fun bind(item: SleepNight) {
            val res = itemView.context.resources
            sleepNightBinding.sleepLength.text =
                convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            sleepNightBinding.qualityString.text =
                convertNumericQualityToString(item.sleepQuality, res)

            sleepNightBinding.qualityImage.setImageResource(
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

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

    }


}