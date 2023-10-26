package com.example.appdoctor.view.findscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdoctor.databinding.ItemFindBinding
import com.example.appdoctor.model.Hospital

class FindHospitalAdapter: ListAdapter<Hospital, FindHospitalAdapter.BenhVienViewHolder>(ITEM_COMPARATOR) {
    var onItemClick: ((Hospital) -> Unit)? = null

    inner class BenhVienViewHolder(val binding: ItemFindBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Hospital) {
            binding.tvName.text = data.TenBenhVien?:""
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