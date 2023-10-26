package com.example.appdoctor.view.file

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.appdoctor.MainActivity
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentDetialFileBinding
import com.example.appdoctor.model.Customer
import com.example.appdoctor.utils.Constant
import com.example.appdoctor.view.homescreen.HomeViewModel

class DetailFileFragment : Fragment() {
    lateinit var binding: FragmentDetialFileBinding
    private val viewModel: HomeViewModel by viewModels()
    private var customer=Customer()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).handleShowBottomNavigation(false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detial_file, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
        arguments?.getParcelable<Customer>(Constant.KEY_CUSTOMERS)?.let{
            customer=it
            setUpView(customer)
        }
    }

    private fun setUpView(customer: Customer) {
        binding.tvid.text=customer.idKH
        binding.tvName.text=customer.tenKH
        binding.tvPhone.text=customer.sdtKH
        binding.tvDate.text=customer.ngaySinhKH
        binding.tvSex.text=customer.gioiTinhKH
        binding.tvEmail.text=customer.emailKH
        binding.tvAddress.text=customer.diaChiKH
        binding.tvWork.text=customer.congviecKH
    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnUpdate.setOnClickListener {
            findNavController().navigate(R.id.action_detailFileFragment_to_newFileFragment , bundleOf(
                Constant.KEY_CUSTOMERS to customer)
            )
        }

    }
}