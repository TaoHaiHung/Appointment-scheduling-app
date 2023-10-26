package com.example.appdoctor.view.homescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdoctor.databinding.ItemHospitalBinding
import com.example.appdoctor.model.Hospital

class HospitalAdapter : ListAdapter<Hospital, HospitalAdapter.BenhVienViewHolder>(ITEM_COMPARATOR) {
    var onItemClick: ((Hospital) -> Unit)? = null

    inner class BenhVienViewHolder(val binding: ItemHospitalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Hospital) {
            binding.tvAddrresHospital.text=data.diaChi?:""
            binding.tvNameHospital.text = data.TenBenhVien?:""
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
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Hospital>() {
            override fun areItemsTheSame(oldItem: Hospital, newItem: Hospital): Boolean {
                return oldItem.idBenhVien == newItem.idBenhVien
            }

            override fun areContentsTheSame(oldItem: Hospital, newItem: Hospital): Boolean {
                return oldItem == newItem
            }

        }
    }
}