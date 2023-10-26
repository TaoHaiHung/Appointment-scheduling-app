package com.example.appdoctor.view.appointment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdoctor.MainActivity
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentAppointmentBinding
import com.example.appdoctor.model.PhieuKham
import com.example.appdoctor.utils.Constant
import com.example.appdoctor.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentFragment : Fragment() {
    private lateinit var binding: FragmentAppointmentBinding
    private val viewModel: AppointmentViewModel by viewModels()
    private lateinit var phieuKhamAdapter: AppointmentAdapter
    private var originalList: List<PhieuKham> = listOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).handleShowBottomNavigation(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        // handleView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerview()
        setUpObserver()
        setUpObserverSearch()
    }

    private fun setUpObserver() {
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val idTK = sharedPreferences.getString("idTK", null).toString()
        viewModel.getPhieuKhamByIdTK(idTK, "").observe(viewLifecycleOwner) { data ->
            when (data.status) {
                DataResult.Status.SUCCESS -> {
                    originalList = data.data?.body()?.listPhieuKham ?: emptyList()
                    showShimmer(false)
                    Log.d("PhieuKham", originalList.toString())
                    phieuKhamAdapter.submitList(ArrayList(originalList))
                    phieuKhamAdapter.onItemClick = { phieukham ->
                        findNavController().navigate(
                            R.id.action_appointmentFragment_to_detailAppointmentFragment, bundleOf(
                                Constant.KEY_PK to phieukham
                            )
                        )
                    }
                }
                DataResult.Status.LOADING -> {
                    showShimmer(true)
                }
                DataResult.Status.ERROR -> {
                    showShimmer(true)
                }
            }
        }
    }

    private fun setUpObserverSearch() {
        binding.edtSearch.doOnTextChanged { text, _, _, _ ->
            val search = text.toString().toLowerCase()
            if (search.isEmpty()) {
                showShimmer(false)
                phieuKhamAdapter.submitList(ArrayList(originalList))
            } else {
                val filteredList = originalList.filter {
                    it.benhnhan?.tenKH?.toLowerCase()?.contains(search) == true
                }
                showShimmer(false)
                phieuKhamAdapter.submitList(ArrayList(filteredList))
            }
        }
    }

    private fun showShimmer(isShow: Boolean) {
        if (isShow) {
            binding.shimmer.visibility = View.VISIBLE
            binding.rvPhieuKham.visibility = View.GONE
            binding.shimmer.startShimmer()
        } else {
            binding.shimmer.visibility = View.GONE
            binding.rvPhieuKham.visibility = View.VISIBLE
            binding.shimmer.stopShimmer()
        }
    }

    private fun setUpRecyclerview() {
        phieuKhamAdapter = AppointmentAdapter()
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvPhieuKham.apply {
            adapter = phieuKhamAdapter
            setLayoutManager(layoutManager)
        }
    }
}
