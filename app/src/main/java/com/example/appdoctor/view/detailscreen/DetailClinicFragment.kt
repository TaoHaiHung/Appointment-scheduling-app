package com.example.appdoctor.view.detailscreen

import android.content.Context
import android.os.Bundle
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
import com.example.appdoctor.databinding.FragmentDetailClinicBinding
import com.example.appdoctor.model.Clinic
import com.example.appdoctor.model.PhieuKham
import com.example.appdoctor.utils.Constant
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailClinicFragment :Fragment(){
    lateinit var binding: FragmentDetailClinicBinding
    private var clinic = Clinic()
    private var phieukham = PhieuKham()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).handleShowBottomNavigation(false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_clinic, container, false)
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
        arguments?.getParcelable<Clinic>(Constant.KEY_CLINIC)?.let{
            clinic=it
            setUpView(clinic)
        }
        arguments?.getParcelable<PhieuKham>(Constant.KEY_PK)?.let {
            phieukham=it
            setUpViewPhieuKham(phieukham)
        }



    }

    private fun setUpViewPhieuKham(phieukham: PhieuKham) {
        Glide.with(binding.ivImageClinic.context).load(phieukham.clinic?.image).into(binding.ivImageClinic)
        binding.tvNameClinic.text=phieukham.clinic?.TenPhongKham
        binding.tvAddress.text=phieukham.clinic?.diaChi
        binding.tvIntro.text=phieukham.clinic?.gioithieu
        binding.tvChuyenSau.text=phieukham.clinic?.chuyenSau
    }

    private fun setUpView(clinic: Clinic) {
        Glide.with(binding.ivImageClinic.context).load(clinic.image).into(binding.ivImageClinic)
        binding.tvNameClinic.text=clinic.TenPhongKham
        binding.tvAddress.text=clinic.diaChi
        binding.tvIntro.text=clinic.gioithieu
        binding.tvChuyenSau.text=clinic.chuyenSau

    }


    private fun initView() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnApply.setOnClickListener {
            findNavController().navigate(R.id.action_detailClinicFragment_to_bookingClinicFragment,
                bundleOf(Constant.KEY_CLINIC to clinic , Constant.KEY_PK to phieukham))
        }


    }
}