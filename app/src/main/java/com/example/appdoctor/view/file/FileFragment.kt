package com.example.appdoctor.view.file

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdoctor.MainActivity
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentFileBinding
import com.example.appdoctor.model.Customer
import com.example.appdoctor.model.CustomerResponse
import com.example.appdoctor.utils.Constant
import com.example.appdoctor.utils.DataResult
import com.example.appdoctor.view.homescreen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FileFragment: Fragment() {
    lateinit var binding :FragmentFileBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var customerAdapter: CustomerAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).handleShowBottomNavigation(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_file, container, false)
        binding.lifecycleOwner = this
        setUpObserverCustomer()
        setUpRecyclerviewCustomer()
        return binding.root
    }

    private fun setUpRecyclerviewCustomer() {
        customerAdapter = CustomerAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvFile.apply {
            adapter = customerAdapter
            setLayoutManager(layoutManager)
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun setUpObserverCustomer() {
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val idTK = sharedPreferences.getString("idTK", null).toString()
        viewModel.getCustomersByUserId(idTK).observe(viewLifecycleOwner){ data->
            when (data.status) {
                DataResult.Status.SUCCESS -> {
                    showShimmer(false)
                    val listCustomer: ArrayList<Customer> = ArrayList()
                    val value = data.data?.body() as CustomerResponse
                    value.listCustomer?.forEach {
                        listCustomer.add(it)
                        Log.d("KhachHang", listCustomer.toString())
                    }
                    customerAdapter?.submitList(listCustomer)
                    customerAdapter?.onItemClick ={customer->
                        findNavController().navigate(R.id.action_fileFragment_to_detailFileFragment , bundleOf(
                            Constant.KEY_CUSTOMERS to customer))
                    }
                }

                DataResult.Status.LOADING -> {
                    showShimmer(false)

                }

                DataResult.Status.ERROR -> {
                    showShimmer(false)


                }
            }

        }
    }

    private fun showShimmer(isShow: Boolean) {
        if(isShow){
            binding.shimmer.visibility=View.VISIBLE
            binding.rvFile.visibility=View.GONE
            binding.shimmer.startShimmer()

        }else{
            binding.shimmer.visibility=View.GONE
            binding.rvFile.visibility=View.VISIBLE
            binding.shimmer.stopShimmer()

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
    }

    private fun setUpListener() {
        binding.ivAdd.setOnClickListener {
            findNavController().navigate(R.id.action_fileFragment_to_newFileFragment)
        }
    }



}