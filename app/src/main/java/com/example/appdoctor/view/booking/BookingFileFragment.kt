package com.example.appdoctor.view.booking

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentBookingFileBinding
import com.example.appdoctor.login.SharedViewModel
import com.example.appdoctor.model.Customer
import com.example.appdoctor.model.CustomerResponse
import com.example.appdoctor.utils.Constant
import com.example.appdoctor.utils.DataResult
import com.example.appdoctor.view.file.CustomerAdapter
import com.example.appdoctor.view.homescreen.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingFileFragment: Fragment() {
    lateinit var binding :FragmentBookingFileBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var customerAdapter: CustomerAdapter
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_booking_file, container, false)
        binding.lifecycleOwner = this
        setUpObserverKhachHang()
        setUpRecyclerviewKhachHang()
        return binding.root
    }

    private fun setUpRecyclerviewKhachHang() {
        customerAdapter = CustomerAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvFile.apply {
            adapter = customerAdapter
            setLayoutManager(layoutManager)
        }
    }


    @SuppressLint("MissingInflatedId")
    private fun setUpObserverKhachHang() {
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val idTK = sharedPreferences.getString("idTK", null).toString()
        viewModel.getCustomersByUserId(idTK).observe(viewLifecycleOwner){ data->
            when (data.status) {
                DataResult.Status.SUCCESS -> {
                    val listCustomer: ArrayList<Customer> = ArrayList()
                    val value = data.data?.body() as CustomerResponse
                    value.listCustomer?.forEach {
                        listCustomer.add(it)
                        Log.d("KhachHang", listCustomer.toString())
                    }
                    customerAdapter?.submitList(listCustomer)
                    customerAdapter?.onItemClick ={customer->
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
                        id.text=customer.idKH
                        name.text=customer.tenKH
                        phone.text=customer.sdtKH
                        date.text=customer.ngaySinhKH
                        sex.text=customer.gioiTinhKH
                        address.text=customer.diaChiKH
                        work.text=customer.congviecKH
                        email.text = if (customer.emailKH.isNullOrEmpty()) "Chưa cập nhật" else customer.emailKH
                        btnUpdate.setOnClickListener {
                            dialog.dismiss()
                            findNavController().navigate(R.id.action_fileFragment_to_newFileFragment , bundleOf(
                                Constant.KEY_CUSTOMERS to customer)
                            )
                        }
                        btnSubmit.setOnClickListener {
//                                findNavController().navigate(R.id.action_bookingFileFragment_to_bookingFragment , bundleOf(
//                                    Constant.KEY_CUSTOMERS to khachhang)
//                                )
                            val sharedPreferences = requireActivity().getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("tenKH", customer.tenKH)
                            editor.putString("birthday", customer.ngaySinhKH)
                            editor.putString("sdt",customer.sdtKH)
                            editor.putString("idKH",customer.idKH)
                            editor.putString("gender",customer.gioiTinhKH)
                            editor.putString("Address",customer.diaChiKH)
                            editor.putString("work",customer.congviecKH)
                            editor.putString("email",customer.emailKH)
                            editor.apply()
                            requireActivity().onBackPressed()
                            dialog.dismiss()
                        }

                        dialog.setCancelable(true)
                        dialog.setContentView(view)
                        dialog.show()

                    }

                }

                DataResult.Status.LOADING -> {

                }

                DataResult.Status.ERROR -> {


                }
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
    }

    private fun setUpListener() {
        binding.btnNewFile.setOnClickListener {
            findNavController().navigate(R.id.action_bookingFileFragment_to_newFileFragment)
        }
    }



}