package com.example.appdoctor.view.homescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdoctor.databinding.ItemHospitalBinding
import com.example.appdoctor.model.Clinic


class ClinicAdapter : ListAdapter<Clinic, ClinicAdapter.BenhVienViewHolder>(ITEM_COMPARATOR) {
    var onItemClick: ((Clinic) -> Unit)? = null

    inner class BenhVienViewHolder(val binding: ItemHospitalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Clinic) {
            binding.tvAddrresHospital.text=data.diaChi?:""
            binding.tvNameHospital.text = data.TenPhongKham?:""
            Glide.with(binding.ivImageHospital.context).load(data.image).into(binding.ivImageHospital)
            binding.root.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BenhVienViewHolder {
        return BenhVienViewHolder(ItemHospitalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BenhVienViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    companion object{
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Clinic>() {
            override fun areItemsTheSame(oldItem: Clinic, newItem: Clinic): Boolean {
                return oldItem.idPhongKham == newItem.idPhongKham
            }

            override fun areContentsTheSame(oldItem: Clinic, newItem: Clinic): Boolean {
                return oldItem == newItem
            }

        }
    }
}