package com.example.appdoctor.view.bookingClinic

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentBookingClinicBinding
import com.example.appdoctor.model.Clinic
import com.example.appdoctor.model.Customer
import com.example.appdoctor.model.PhieuKham
import com.example.appdoctor.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingClinicFragment :Fragment() {
    lateinit var binding: FragmentBookingClinicBinding
    private var clinic = Clinic()
    private var phieuKham= PhieuKham()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_booking_clinic, container, false)
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpListener()
        arguments?.getParcelable<Clinic>(Constant.KEY_CLINIC)?.let {
            clinic = it
            setUpView(clinic)
        }
        arguments?.getParcelable<PhieuKham>(Constant.KEY_PK)?.let {
            phieuKham = it
            setUpViewByPhieuKham(phieuKham)
        }
        handleView()
        handleBtnContinue()
    }

    private fun setUpViewByPhieuKham(phieuKham: PhieuKham) {
        if (phieuKham.clinic?.idPhongKham?.isNotEmpty()==true){
            binding.tvName.text=phieuKham.clinic.TenPhongKham
        }
        else{
            setUpView(clinic)
        }
    }

    private fun handleView() {
        val addressText = binding.tvAddress.text.toString()
        val dateText = binding.tvDate.text.toString()
        val hourText = binding.tvHour.text.toString()
        val customerText = binding.tvCustomer.text.toString()
        if(addressText.isNotEmpty()){
            binding.llTotalDate.visibility=View.VISIBLE
        }
        if(dateText.isNotEmpty()){
            binding.llTotalHour.visibility=View.VISIBLE
        }
        if(hourText.isNotEmpty()){
            binding.llTotalPerson.visibility=View.VISIBLE
        }
        if(customerText.isNotEmpty()){
            binding.llTotalNote.visibility=View.VISIBLE
        }

    }

    private fun handleBtnContinue() {
        val addressText = binding.tvAddress.text.toString()
        val dateText = binding.tvDate.text.toString()
        val hourText = binding.tvHour.text.toString()
        val customerText = binding.tvCustomer.text.toString()
        if (addressText.isEmpty() ||dateText.isEmpty() || hourText.isEmpty() || customerText.isEmpty()) {
            binding.btnContinue.isEnabled = false
            binding.btnContinue.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorTextSecondary))
        } else {
            binding.btnContinue.isEnabled = true
            binding.btnContinue.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorBackground))

        }
    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.llAddress.setOnClickListener {
            findNavController().navigate(R.id.action_bookingClinicFragment_to_addressClinicFragment,
                bundleOf(Constant.KEY_CLINIC to clinic, Constant.KEY_PK to phieuKham)
            )
        }
        binding.llDate.setOnClickListener {
            findNavController().navigate(R.id.action_bookingClinicFragment_to_calendarFragment)
        }
        binding.llFilePerson.setOnClickListener {
            findNavController().navigate(R.id.action_bookingClinicFragment_to_bookingFileFragment)
        }
        binding.llHour.setOnClickListener {
            findNavController().navigate(R.id.action_bookingClinicFragment_to_scheduleFragment,
                bundleOf(Constant.KEY_CLINIC to clinic, Constant.KEY_PK to phieuKham))
        }
        binding.btnContinue.setOnClickListener {
            findNavController().navigate(
                R.id.action_bookingClinicFragment_to_detailBookingFragment,
                bundleOf(Constant.KEY_CLINIC to clinic, Constant.KEY_PK to phieuKham)
            )
        }

    }

    private fun setUpView(clinic: Clinic) {
        binding.tvName.text=clinic.TenPhongKham
//        val sharedPreferences = requireActivity().getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putString("idClinic", clinic.idPhongKham)
//        editor.apply()
    }

    private fun initView() {
        val sharedPreferences = requireActivity().getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
        binding.tvAddress.text = sharedPreferences.getString("address", null)
        binding.tvDate.text = sharedPreferences.getString("date", null)
        binding.tvCustomer.text = sharedPreferences.getString("tenKH", null)
        binding.tvHour.text = sharedPreferences.getString("hour", null)

    }
}
