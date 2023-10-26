package com.example.appdoctor.view.findscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdoctor.databinding.ItemFindBinding
import com.example.appdoctor.model.Clinic


class FindClinicAdapter: ListAdapter<Clinic, FindClinicAdapter.BenhVienViewHolder>(ITEM_COMPARATOR) {
    var onItemClick: ((Clinic) -> Unit)? = null

    inner class BenhVienViewHolder(val binding: ItemFindBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Clinic) {
            binding.tvName.text = data.TenPhongKham?:""
            binding.tvAddress.text=data.diaChi
            Glide.with(binding.ivImage.context).load(data.image).into(binding.ivImage)
            binding.root.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BenhVienViewHolder {
        return BenhVienViewHolder(ItemFindBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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