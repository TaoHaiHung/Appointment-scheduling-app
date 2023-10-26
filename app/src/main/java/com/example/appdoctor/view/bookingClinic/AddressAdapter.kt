package com.example.appdoctor.view.bookingClinic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appdoctor.databinding.ItemAddressClinicBinding
import com.example.appdoctor.model.Address


class AddressAdapter : ListAdapter<Address, AddressAdapter.AddressClinicViewHolder>(ITEM_COMPARATOR) {
    var onItemClick: ((Address) -> Unit)? = null

    inner class AddressClinicViewHolder(val binding: ItemAddressClinicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Address) {
            binding.tvName.text = data.nameCS
            binding.tvAddress.text = data.address
            binding.root.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressClinicViewHolder {
        return AddressClinicViewHolder(ItemAddressClinicBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AddressClinicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    companion object{
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Address>() {
            override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
                return oldItem.idCS == newItem.idCS
            }
            override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
                return oldItem == newItem
            }

        }
    }
}