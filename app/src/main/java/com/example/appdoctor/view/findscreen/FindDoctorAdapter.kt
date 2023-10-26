package com.example.appdoctor.view.findscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdoctor.databinding.ItemFindBinding
import com.example.appdoctor.model.Doctor

class FindDoctorAdapter: ListAdapter<Doctor, FindDoctorAdapter.BacSiViewHolder>(ITEM_COMPARATOR) {
    var onItemClick: ((Doctor) -> Unit)? = null

    inner class BacSiViewHolder(val binding: ItemFindBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Doctor) {
            binding.tvName.text = data.TenBacSi?:""
            binding.tvChuyenKhoa.text=data.kinhnghiem
            binding.tvAddress.text=data.diaChi
            Glide.with(binding.ivImage.context).load(data.image).into(binding.ivImage)
            binding.root.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BacSiViewHolder {
        return BacSiViewHolder(ItemFindBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BacSiViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    companion object{
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Doctor>() {
            override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
                return oldItem.idBacSi == newItem.idBacSi
            }

            override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
                return oldItem == newItem
            }

        }
    }
}