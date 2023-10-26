package com.example.appdoctor.view.booking

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentScheduleBinding
import com.example.appdoctor.model.Clinic
import com.example.appdoctor.model.Doctor
import com.example.appdoctor.model.PhieuKham
import com.example.appdoctor.model.Schedule
import com.example.appdoctor.model.ScheduleResponse
import com.example.appdoctor.utils.Constant
import com.example.appdoctor.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment :Fragment() {
    lateinit var binding: FragmentScheduleBinding
    private val viewModel: BookingViewModel by viewModels()
    private lateinit var scheduleAdapter: ScheduleDoctorAdapter
    private var doctor = Doctor()
    private var clinic= Clinic()
    private var phieuKham=PhieuKham()
    private var idBS :String =""
    private var idClinic :String =""
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Doctor>(Constant.KEY_DOCTOR)?.let {
            doctor = it
            setUpView(doctor)
        }
        arguments?.getParcelable<Clinic>(Constant.KEY_CLINIC)?.let {
            clinic = it
            setUpViewClinic(clinic)
        }
        arguments?.getParcelable<PhieuKham>(Constant.KEY_PK)?.let {
            phieuKham = it
            setUpViewByPhieuKham(phieuKham)
        }
        setUpRecyclerviewScheduleByDoctor()
        setUpRecyclerviewScheduleByClinic()
        setUpObserverSchedule()
        setUpListeners()
    }

    private fun setUpViewByPhieuKham(phieuKham: PhieuKham) {
        when{
            phieuKham.doctor?.idBacSi?.isNotEmpty()==true ->{
                idBS=phieuKham.doctor.idBacSi
            }
            phieuKham.clinic?.idPhongKham?.isNotEmpty()==true ->{
                idClinic=phieuKham.clinic.idPhongKham
            }

        }

    }

    private fun setUpRecyclerviewScheduleByClinic() {
        viewModel.getScheduleByClinic(idClinic).observe(viewLifecycleOwner) { data ->
            when (data.status) {
                DataResult.Status.SUCCESS -> {
                    val listSchedule: ArrayList<Schedule> = ArrayList()
                    val value = data.data?.body() as ScheduleResponse
                    value.listSchedule?.forEach {
                        listSchedule.add(it)
                        Log.d("setUpObserver", listSchedule.toString())

                    }
                    scheduleAdapter?.submitList(listSchedule)
                    scheduleAdapter?.onItemClick ={schedule->
                        val sharedPreferences = requireActivity().getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("hour", schedule.time)
                        editor.apply()
                        requireActivity().onBackPressed()

                    }

                }

                DataResult.Status.LOADING -> {

                }

                DataResult.Status.ERROR -> {


                }
            }
        }
    }

    private fun setUpViewClinic(clinic: Clinic) {
        if(clinic.idPhongKham?.isNotEmpty()==true){
            idClinic=clinic.idPhongKham
        }
        else{
            setUpViewByPhieuKham(phieuKham)
        }
    }

    private fun setUpListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpView(doctor: Doctor) {
        if (doctor.idBacSi?.isNotEmpty()==true){
            idBS=doctor.idBacSi
        }
        else{
            setUpViewByPhieuKham(phieuKham)
        }
    }

    private fun setUpObserverSchedule() {
        scheduleAdapter= ScheduleDoctorAdapter()
        val layoutManager=
            GridLayoutManager(requireContext(),3)
        binding.rvHour.apply {
            adapter=scheduleAdapter
            setLayoutManager(layoutManager)
        }
    }

    private fun setUpRecyclerviewScheduleByDoctor() {
            viewModel.getScheduleByDoctor(idBS).observe(viewLifecycleOwner) { data ->
                when (data.status) {
                    DataResult.Status.SUCCESS -> {
                        val listSchedule: ArrayList<Schedule> = ArrayList()
                        val value = data.data?.body() as ScheduleResponse
                        value.listSchedule?.forEach {
                            listSchedule.add(it)
                            Log.d("setUpObserver", listSchedule.toString())

                        }
                        scheduleAdapter?.submitList(listSchedule)
                        scheduleAdapter?.onItemClick ={schedule->
                            val sharedPreferences = requireActivity().getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("hour", schedule.time)
                            editor.apply()
                            requireActivity().onBackPressed()

                        }

                    }

                    DataResult.Status.LOADING -> {

                    }

                    DataResult.Status.ERROR -> {


                    }
                }
            }
        }

    }
