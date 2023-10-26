package com.example.appdoctor.view.appointment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdoctor.databinding.ItemPhieuKhamBinding
import com.example.appdoctor.model.PhieuKham


class AppointmentAdapter : ListAdapter<PhieuKham, AppointmentAdapter.PhieuKhamViewHolder>(ITEM_COMPARATOR), Filterable {
    var onItemClick: ((PhieuKham) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhieuKhamViewHolder {
        return PhieuKhamViewHolder(
            ItemPhieuKhamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhieuKhamViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                val filteredList = ArrayList<PhieuKham>()

                if (charString.isEmpty()) {
                    filteredList.addAll(currentList)
                } else {
                    for (pk in currentList) {
                        if ((pk.idPK)?.lowercase()?.contains(charString.lowercase()) == true) {
                            filteredList.add(pk)
                        }
                    }
                }

                val filterResult = FilterResults()
                filterResult.values = filteredList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as List<PhieuKham>?)
            }
        }
    }

    fun setupData(newData: List<PhieuKham>) {
        submitList(newData)
    }

    inner class PhieuKhamViewHolder(val binding: ItemPhieuKhamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PhieuKham) {
            when{
                data.IdBS?.isNotEmpty()==true->{
                    Glide.with(binding.ivImage.context).load(data.doctor?.image).into(binding.ivImage)
                    binding.tvName.text = data.doctor?.TenBacSi
                }
                data.idCLinic?.isNotEmpty()==true->{
                    Glide.with(binding.ivImage.context).load(data.clinic?.image).into(binding.ivImage)
                    binding.tvName.text = data.clinic?.TenPhongKham
                }

            }

            binding.tvHour.text = data.gioKham
            binding.tvNameCustomer.text = data.benhnhan?.tenKH
            binding.tvStt.text = data.stt.toString()
            binding.tvStatus.text = data.tinhTrang
            when (data.tinhTrang) {
                "Đã đăng ký" -> binding.tvStatus.setTextColor(Color.GREEN)
                "Đã hủy" -> binding.tvStatus.setTextColor(Color.RED)
                else -> binding.tvStatus.setTextColor(Color.BLACK)
            }
            binding.tvDate.text = data.ngayKham

            binding.root.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<PhieuKham>() {
            override fun areItemsTheSame(oldItem: PhieuKham, newItem: PhieuKham): Boolean {
                return oldItem.idPK == newItem.idPK
            }

            override fun areContentsTheSame(oldItem: PhieuKham, newItem: PhieuKham): Boolean {
                return oldItem == newItem
            }
        }
    }
}
