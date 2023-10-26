package com.example.appdoctor.view.bookingClinic

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentAddressClinicBinding
import com.example.appdoctor.model.Address
import com.example.appdoctor.model.AddressResponse
import com.example.appdoctor.model.Clinic
import com.example.appdoctor.model.PhieuKham
import com.example.appdoctor.utils.Constant
import com.example.appdoctor.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddressClinicFragment :Fragment() {
    lateinit var binding: FragmentAddressClinicBinding
    private val viewModel: BookingClinicViewModel by viewModels()
    private lateinit var addressAdapter: AddressAdapter
    private var clinic = Clinic()
    private var phieuKham =PhieuKham()
    private var idClinic :String =""
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_address_clinic, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Clinic>(Constant.KEY_CLINIC)?.let {
            clinic = it
            setUpView(clinic)
        }
        arguments?.getParcelable<PhieuKham>(Constant.KEY_PK)?.let {
            phieuKham = it
            setUpViewByPhieuKham(phieuKham)
        }
        setUpRecyclerview()
        setUpObserverSchedule()
        setUpListeners()
    }

    private fun setUpViewByPhieuKham(phieuKham: PhieuKham) {
        if (phieuKham.clinic?.idPhongKham?.isNotEmpty()==true){
            idClinic=phieuKham.clinic?.idPhongKham.toString()
        }


    }

    private fun setUpListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpView(clinic: Clinic) {
        if(clinic.idPhongKham?.isNotEmpty()==true){
            idClinic=clinic.idPhongKham
        }
        else{
            setUpViewByPhieuKham(phieuKham)
        }

    }

    private fun setUpObserverSchedule() {
        addressAdapter= AddressAdapter()
        val layoutManager=
            LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.rvAddress.apply {
            adapter=addressAdapter
            setLayoutManager(layoutManager)
        }
    }

    private fun setUpRecyclerview() {
        viewModel.getAddressByClinic(idClinic).observe(viewLifecycleOwner) { data ->
            when (data.status) {
                DataResult.Status.SUCCESS -> {
                    val listAddress: ArrayList<Address> = ArrayList()
                    val value = data.data?.body() as AddressResponse
                    value.listAddress?.forEach {
                        listAddress.add(it)
                        Log.d("setUpObserver", listAddress.toString())

                    }
                    addressAdapter?.submitList(listAddress)
                    addressAdapter?.onItemClick ={address->
                        val sharedPreferences = requireActivity().getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("address", address.nameCS)
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