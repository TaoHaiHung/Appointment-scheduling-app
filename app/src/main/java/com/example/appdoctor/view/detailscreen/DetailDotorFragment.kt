package com.example.appdoctor.view.detailscreen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.appdoctor.MainActivity
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentDetailDoctorBinding
import com.example.appdoctor.model.Doctor
import com.example.appdoctor.model.PhieuKham
import com.example.appdoctor.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailDotorFragment :Fragment(){
    lateinit var binding: FragmentDetailDoctorBinding
    private var doctor = Doctor()
    private var phieukham = PhieuKham()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).handleShowBottomNavigation(false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_doctor, container, false)
        binding.lifecycleOwner = this
        val sharedPreferences = requireActivity().getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        arguments?.getParcelable<Doctor>(Constant.KEY_DOCTOR)?.let{
            doctor=it
            setUpView(doctor)
        }
        arguments?.getParcelable<PhieuKham>(Constant.KEY_PK)?.let {
            phieukham=it
            setUpViewPK(phieukham)

        }
    }

    private fun setUpViewPK(phieukham: PhieuKham) {
        // bundle tu phieu kham
        Glide.with(binding.ivImage.context).load(phieukham.doctor?.image).into(binding.ivImage)
        binding.tvName.text=phieukham.doctor?.TenBacSi
        binding.tvHeader.text=phieukham.doctor?.TenBacSi
        binding.tvAddress.text=phieukham.doctor?.diaChi
        binding.tvGioiThieu.text=phieukham.doctor?.gioithieu
        binding.tvHospital.text=phieukham.doctor?.hospital?.TenBenhVien
        binding.tvDep.text=phieukham.doctor?.chuyenkhoa
        binding.tvExp.text=phieukham.doctor?.kinhnghiem
        Log.d("DetailPhieuKham", phieukham.toString())

    }

    private fun setUpView(doctor: Doctor ) {
        Glide.with(binding.ivImage.context).load(doctor.image).into(binding.ivImage)
        binding.tvName.text=doctor.TenBacSi
        binding.tvHeader.text=doctor.TenBacSi
        binding.tvExp.text=doctor.kinhnghiem
        binding.tvAddress.text=doctor.diaChi
        binding.tvGioiThieu.text=doctor.gioithieu
        binding.tvDep.text=doctor.chuyenkhoa
        binding.tvHospital.text=doctor.hospital?.TenBenhVien


    }

    private fun initView() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnBooking.setOnClickListener {
            findNavController().navigate(R.id.action_detailDotorFragment_to_bookingFragment ,bundleOf(
                    Constant.KEY_DOCTOR to doctor, Constant.KEY_PK to phieukham))
        }

    }
}