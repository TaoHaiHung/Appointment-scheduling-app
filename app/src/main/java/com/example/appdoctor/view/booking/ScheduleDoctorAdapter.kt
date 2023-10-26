package com.example.appdoctor.view.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appdoctor.databinding.ItemScheduleBinding

import com.example.appdoctor.model.Schedule

class ScheduleDoctorAdapter : ListAdapter<Schedule, ScheduleDoctorAdapter.ScheduleDoctorViewHolder>(ITEM_COMPARATOR) {
    var onItemClick: ((Schedule) -> Unit)? = null

    inner class ScheduleDoctorViewHolder(val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Schedule) {
            binding.tvTime.text = data.time?:""
            binding.root.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleDoctorViewHolder {
        return ScheduleDoctorViewHolder(ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ScheduleDoctorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    companion object{
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Schedule>() {
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.idSchedule == newItem.idSchedule
            }

            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem == newItem
            }

        }
    }
}