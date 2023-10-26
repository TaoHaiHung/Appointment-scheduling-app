package com.example.appdoctor.view.booking

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentSuccessDetailBinding
import com.example.appdoctor.model.PhieuKham
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SuccessDetailFragment : Fragment(){
    private lateinit var binding: FragmentSuccessDetailBinding
    private val viewModel: BookingViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private var listPhieuKham: ArrayList<PhieuKham>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_success_detail, container, false)
        binding.lifecycleOwner = this
        sharedPreferences = requireActivity().getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { args ->
            // Retrieve the list from arguments
            listPhieuKham = args.getParcelableArrayList("listPhieuKham")
            Log.d("listPK", listPhieuKham.toString())

            // Check if the list is not null
            listPhieuKham?.let { list ->
                // Perform actions with the list
                for (phieukham in list) {
                    setUpView(phieukham)

                }
            }
        }
        intView()

        }

    private fun setUpView(phieukham: PhieuKham) {

        when{
            phieukham.IdBS?.isNotEmpty() == true ->{
                Glide.with(binding.ivImage.context).load(phieukham.doctor?.image).into(binding.ivImage)
                binding.tvNameDoctor.text=phieukham.doctor?.TenBacSi
                binding.tvTitleDep.text="Chuyên Khoa"
                binding.tvDep.text=phieukham.doctor?.chuyenkhoa
            }
            phieukham.idCLinic?.isNotEmpty()==true->{
                Glide.with(binding.ivImage.context).load(phieukham.clinic?.image).into(binding.ivImage)
                binding.tvNameDoctor.text=phieukham.clinic?.TenPhongKham
                binding.tvTitleDep.text="Địa Chỉ"
                binding.tvDep.text=sharedPreferences.getString("address",null)
            }
        }
        binding.tvIdPK.text=phieukham.idPK
        binding.tvDateBooking.text=phieukham.ngayKham
        binding.tvHour.text=phieukham.gioKham
        binding.tvStt.text=phieukham.stt
        binding.tvIdCustomer.text=phieukham.benhnhan?.idKH
        binding.tvNameCustomer.text=phieukham.benhnhan?.tenKH
        binding.tvDate.text=phieukham.benhnhan?.ngaySinhKH

        when (phieukham.tinhTrang) {
            "Đã đăng ký" -> binding.tvStatus.setTextColor(Color.GREEN)
            "Đã hủy" -> {
                binding.tvStatus.setTextColor(Color.RED)
            }
            else -> binding.tvStatus.setTextColor(Color.BLACK) // Màu mặc định nếu không khớp
        }
        binding.tvStatus.text=phieukham.tinhTrang
    }


    private fun intView() {
        binding.ivHome.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        handleDetailCustomer()
    }
    private fun handleDetailCustomer() {
        binding.btnDetail.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
            var view= layoutInflater.inflate(R.layout.bottom_dialog_file,null)
            // handleView
            var id=view.findViewById<TextView>(R.id.tvid)
            var name=view.findViewById<TextView>(R.id.tvName)
            var phone=view.findViewById<TextView>(R.id.tvPhone)
            var date=view.findViewById<TextView>(R.id.tvDate)
            var sex=view.findViewById<TextView>(R.id.tvSex)
            var address=view.findViewById<TextView>(R.id.tvAddress)
            var work=view.findViewById<TextView>(R.id.tvWork)
            var email=view.findViewById<TextView>(R.id.tvEmail)
            var btnUpdate= view.findViewById<AppCompatButton>(R.id.btnUpdate)
            var btnSubmit= view.findViewById<AppCompatButton>(R.id.btnSubmit)
            var llOption=view.findViewById<LinearLayout>(R.id.llOption)
            llOption.visibility=View.GONE
            id.text=sharedPreferences.getString("idKH",null)
            name.text=sharedPreferences.getString("tenKH",null)
            phone.text=sharedPreferences.getString("sdt","Chưa cập nhật")?:"Chưa cập nhật"
            date.text=sharedPreferences.getString("birthday",null)
            sex.text=sharedPreferences.getString("gender",null)
            address.text=sharedPreferences.getString("Address","Chưa cập nhật")?:"Chưa cập nhật"
            work.text=sharedPreferences.getString("work","Chưa cập nhật")?:"Chưa cập nhật"
            email.text = sharedPreferences.getString("email","Chưa cập nhật")?:"Chưa cập nhật"
            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
        }
    }


}




