package com.example.appdoctor.view.homescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdoctor.databinding.ItemChuyenKhoaBinding
import com.example.appdoctor.model.Specialty


class SpecialtyAdapter : ListAdapter<Specialty, SpecialtyAdapter.ChuyenKhoaViewHolder>(ITEM_COMPARATOR) {
    var onItemClick: ((Specialty) -> Unit)? = null

    inner class ChuyenKhoaViewHolder(val binding: ItemChuyenKhoaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Specialty) {
            binding.tvNameChuyenKhoa.text = data.Ten?:""
            Glide.with(binding.ivChuyenKhoa.context).load(data.image).into(binding.ivChuyenKhoa)
            binding.root.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChuyenKhoaViewHolder {
        return ChuyenKhoaViewHolder(ItemChuyenKhoaBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ChuyenKhoaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    // Thay đổi danh sách chuyên khoa ban đầu chỉ chứa 6 phần tử đầu tiên
//     fun setChuyenKhoaList(chuyenKhoaList: List<ChuyenKhoa>) {
//        submitList(chuyenKhoaList.take(6))
//    }
    companion object{
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Specialty>() {
            override fun areItemsTheSame(oldItem: Specialty, newItem: Specialty): Boolean {
                return oldItem.idChuyenKhoa == newItem.idChuyenKhoa
            }

            override fun areContentsTheSame(oldItem: Specialty, newItem: Specialty): Boolean {
                return oldItem == newItem
            }

        }
    }
}


