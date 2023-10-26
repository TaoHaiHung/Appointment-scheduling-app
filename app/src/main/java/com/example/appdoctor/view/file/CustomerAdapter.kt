package com.example.appdoctor.view.file

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appdoctor.databinding.ItemFileBinding
import com.example.appdoctor.model.Customer



class CustomerAdapter : ListAdapter<Customer, CustomerAdapter.FileViewHolder>(ITEM_COMPARATOR) {
    var onItemClick: ((Customer) -> Unit)? = null

    inner class FileViewHolder(val binding: ItemFileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Customer) {
            binding.tvName.text = data.tenKH?:""
            binding.tvPhone.text=data.sdtKH
            binding.tvDate.text=data.ngaySinhKH
            val nameFirt = data.tenKH?.get(0).toString();
            binding.tvSig.text=if (data.tenKH?.startsWith(nameFirt)== true) nameFirt else ""
//            Glide.with(binding.ivImage.context).load(data.image).into(binding.ivImage)
            binding.root.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder{
        return FileViewHolder(ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    companion object{
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Customer>() {
            override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem.idKH == newItem.idKH
            }

            override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem == newItem
            }

        }
    }
}


