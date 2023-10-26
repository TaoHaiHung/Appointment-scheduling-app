package com.example.appdoctor.view.findscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdoctor.MainActivity
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentFindDoctorBinding
import com.example.appdoctor.model.Clinic
import com.example.appdoctor.model.ClinicResponse
import com.example.appdoctor.model.Doctor
import com.example.appdoctor.model.DoctorResponse
import com.example.appdoctor.model.Hospital
import com.example.appdoctor.model.HospitalResponse
import com.example.appdoctor.utils.Constant
import com.example.appdoctor.utils.DataResult
import com.example.appdoctor.view.homescreen.HomeViewModel
import com.github.ihermandev.advancedradiogroup.AdvancedRadioGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindFragment :Fragment(){
    lateinit var binding : FragmentFindDoctorBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var doctorAdapter: FindDoctorAdapter
    private lateinit var hosptialAdapter: FindHospitalAdapter
    private lateinit var cliniclAdapter: FindClinicAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).handleShowBottomNavigation(false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_find_doctor, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setUpRecyclerviewDoctor()
        setUpObserverDoctor()
        setUpRecyclerviewBenhVien()
        setUpObserverBenhVien()
        setUpRecyclerviewClinic()
        setUpObserverClinic()
        return binding.root
    }

    private fun setUpObserverClinic() {
        viewModel.getListPhongKham().observe(viewLifecycleOwner) { data ->
            when (data.status) {
                DataResult.Status.SUCCESS -> {
                    val listClinic: ArrayList<Clinic> = ArrayList()
                    val value = data.data?.body() as ClinicResponse
                    value.listClinic?.forEach {
                        listClinic.add(it)
                        Log.d("setUpObserver", listClinic.toString())

                    }
                    cliniclAdapter?.submitList(listClinic)
                    cliniclAdapter?.onItemClick ={clinic->


                    }

                }

                DataResult.Status.LOADING -> {

                }

                DataResult.Status.ERROR -> {


                }
            }
        }
    }

    private fun setUpRecyclerviewClinic() {
        cliniclAdapter = FindClinicAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvClinic.apply {
            adapter = cliniclAdapter
            setLayoutManager(layoutManager)
        }
    }

    private fun setUpObserverBenhVien() {
        viewModel.getListBenhVien().observe(viewLifecycleOwner) { data ->
            when (data.status) {
                DataResult.Status.SUCCESS -> {

                    val listHospital: ArrayList<Hospital> = ArrayList()
                    val value = data.data?.body() as HospitalResponse
                    value.listHospital?.forEach {
                        listHospital.add(it)
                        Log.d("setUpObserver", listHospital.toString())

                    }
                    hosptialAdapter?.submitList(listHospital)
                    hosptialAdapter?.onItemClick ={hospital->

                    }

                }

                DataResult.Status.LOADING -> {

                }

                DataResult.Status.ERROR -> {


                }
            }
        }
    }

    private fun setUpRecyclerviewBenhVien() {
        hosptialAdapter = FindHospitalAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvHospital.apply {
            adapter = hosptialAdapter
            setLayoutManager(layoutManager)
        }

    }

    private fun setUpObserverDoctor() {
        viewModel.getListBacSi().observe(viewLifecycleOwner) { data ->
            when (data.status) {
                DataResult.Status.SUCCESS -> {

                    val listDoctor: ArrayList<Doctor> = ArrayList()
                    val value = data.data?.body() as DoctorResponse
                    value.listDoctor?.forEach {
                        listDoctor.add(it)
                        Log.d("setUpObserver", listDoctor.toString())
                    }
                    doctorAdapter.submitList(listDoctor)
                    doctorAdapter.onItemClick ={doctor->
                        findNavController().navigate(R.id.action_findFragment_to_bookingFragment, bundleOf(
                            Constant.KEY_DOCTOR to doctor)
                        )
                    }

                }

                DataResult.Status.LOADING -> {

                }

                DataResult.Status.ERROR -> {


                }
            }
        }
    }

    private fun setUpRecyclerviewDoctor() {
        doctorAdapter = FindDoctorAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvDoctor.apply {
            adapter = doctorAdapter
            setLayoutManager(layoutManager)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
        handleFind()
    }
    private fun handleFind() {
        binding.cardviewFind.setOnClickListener{
            val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            var view= layoutInflater.inflate(R.layout.bottom_dialog_find, null)
            // handleView
            var radBacSi = view.findViewById<RadioButton>(R.id.radBacSi)
            var radBenhVien = view.findViewById<RadioButton>(R.id.radBenhVien)
            var radPhongKham = view.findViewById<RadioButton>(R.id.radPhongKham)
            var radTatCa = view.findViewById<RadioButton>(R.id.radTatCa)
            var radGroup =view.findViewById<AdvancedRadioGroup>(R.id.radGroup)
            var btnApply =view.findViewById<Button>(R.id.btnApply)
            radTatCa.isChecked=true

            // lắng nghe cho RadioGroup
            radGroup.setOnCheckedChangeListener{ group, checkedId ->
                when(checkedId){
                    R.id.radBacSi -> {
                        // Xử lý khi radBacSi được chọn
                        binding.tvLuaChon.text= "Bác sĩ"
                        binding.rvDoctor.visibility=View.VISIBLE
                        binding.rvClinic.visibility=View.GONE
                        binding.rvHospital.visibility=View.GONE


                    }
                    R.id.radBenhVien -> {
                        // Xử lý khi radBenhVien được chọn
                        binding.tvLuaChon.text= "Bệnh viện"
                        binding.rvHospital.visibility=View.VISIBLE
                        binding.rvClinic.visibility=View.GONE
                        binding.rvDoctor.visibility=View.GONE


                    }
                    R.id.radPhongKham -> {
                        // Xử lý khi radPhongKham được chọn
                        binding.tvLuaChon.text= "Phòng khám"
                        binding.rvHospital.visibility=View.GONE
                        binding.rvDoctor.visibility=View.GONE



                    }
                    R.id.radTatCa -> {
                        // Xử lý khi radTatCa được chọn
                        binding.tvLuaChon.text= "Tất cả"
                        binding.rvHospital.visibility=View.VISIBLE
                        binding.rvDoctor.visibility=View.VISIBLE
                        binding.rvClinic.visibility=View.VISIBLE


                    }
                }
            }
            btnApply.setOnClickListener {
                dialog.dismiss()

            }

            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
        }
    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}