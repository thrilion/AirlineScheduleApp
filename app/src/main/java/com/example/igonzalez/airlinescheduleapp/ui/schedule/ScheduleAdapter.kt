package com.example.igonzalez.airlinescheduleapp.ui.schedule

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.igonzalez.airlinescheduleapp.R
import com.example.igonzalez.airlinescheduleapp.databinding.ItemScheduleBinding
import com.example.igonzalez.airlinescheduleapp.model.Entities

class ScheduleAdapter(private val context: Context, private val clickListener: (Entities.Schedule) -> Unit) :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    private var schedules: List<Entities.Schedule> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: ItemScheduleBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_schedule, parent, false)
        return ScheduleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(schedules[position], clickListener)
    }

    fun updateSchedules(schedules: List<Entities.Schedule>) {
        this.schedules = schedules
        notifyDataSetChanged()
    }

    class ScheduleViewHolder(private val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: Entities.Schedule, clickListener: (Entities.Schedule) -> Unit) {
            binding.schedule = schedule
            binding.cardView.setOnClickListener { clickListener(schedule) }
            binding.executePendingBindings()
        }
    }
}