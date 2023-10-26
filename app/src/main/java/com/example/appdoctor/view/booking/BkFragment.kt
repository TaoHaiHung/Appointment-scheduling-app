package com.example.appdoctor.view.booking

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentBookingBinding
import com.example.appdoctor.model.Doctor
import com.example.appdoctor.model.PhieuKham
import com.example.appdoctor.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BkFragment : Fragment() {
    private lateinit var binding: FragmentBookingBinding
    private var doctor = Doctor()
    private var phieuKham=PhieuKham()
    private var idBS :String =""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_booking, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Doctor>(Constant.KEY_DOCTOR)?.let {
            doctor = it
            setUpView(doctor)
        }
        arguments?.getParcelable<PhieuKham>(Constant.KEY_PK)?.let {
            phieuKham = it
            setUpViewDoctorByPhieuKham(phieuKham)
        }

        setUpListeners()
        initData()
        handlebtnContinue()
    }

    private fun setUpViewDoctorByPhieuKham(phieuKham: PhieuKham) {
        if (phieuKham.doctor?.idBacSi?.isNotEmpty()==true){
            Glide.with(binding.ivImage.context)
                .load(phieuKham.doctor?.image)
                .into(binding.ivImage)
            binding.tvName.text = phieuKham.doctor?.TenBacSi
        }
        else{
            setUpView(doctor)
        }

    }

    private fun handlebtnContinue() {
            val dateText = binding.tvDate.text.toString()
            val hourText = binding.tvHour.text.toString()
            val customerText = binding.tvCustomer.text.toString()

            if (dateText.isEmpty() || hourText.isEmpty() || customerText.isEmpty()) {
                binding.btnContinue.isEnabled = false
                binding.btnContinue.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorTextSecondary))
            } else {
                binding.btnContinue.isEnabled = true
                binding.btnContinue.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorBackground))

            }

    }


    private fun setUpView(doctor: Doctor) {
        Glide.with(binding.ivImage.context)
            .load(doctor.image)
            .into(binding.ivImage)
        binding.tvName.text = doctor.TenBacSi
        Log.d("doctor", doctor.toString())
        val sharedPreferences = requireActivity().getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("idBS", doctor.idBacSi)
        editor.apply()
    }

    private fun initData() {
        val sharedPreferences = requireActivity().getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
        binding.tvDate.text = sharedPreferences.getString("date", null)
        binding.tvCustomer.text = sharedPreferences.getString("tenKH", null)
        binding.tvHour.text = sharedPreferences.getString("hour", null)
    }

    private fun setUpListeners() {
        binding.llDate.setOnClickListener {
            findNavController().navigate(R.id.action_bookingFragment_to_calendarFragment)
        }

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.llFilePerson.setOnClickListener {
            findNavController().navigate(R.id.action_bookingFragment_to_bookingFileFragment)
        }

        binding.llHour.setOnClickListener {
            findNavController().navigate(
                R.id.action_bookingFragment_to_scheduleFragment,
                bundleOf(Constant.KEY_DOCTOR to doctor, Constant.KEY_PK to phieuKham)
            )
        }
        binding.btnContinue.setOnClickListener {
            findNavController().navigate(
                R.id.action_bookingFragment_to_detailBookingFragment,
                bundleOf(Constant.KEY_DOCTOR to doctor, Constant.KEY_PK to phieuKham)
            )
        }


    }
}
