package com.example.appdoctor.view.file

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appdoctor.MainActivity
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentNewFileBinding
import com.example.appdoctor.login.SharedViewModel
import com.example.appdoctor.model.Customer
import com.example.appdoctor.model.CustomerResponse
import com.example.appdoctor.utils.Constant
import com.example.appdoctor.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class NewFileFragment :Fragment() {
    lateinit var binding: FragmentNewFileBinding
    private val viewModel: NewFileViewModel by viewModels()
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var customerAdapter: CustomerAdapter
    private var customer=Customer()
    val formatDate = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).handleShowBottomNavigation(false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_file, container, false)
        binding.lifecycleOwner = this
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        setUpObserverKhachHang()
//        val userId= customer.IdTK
//        Log.d("userId", "onCreateView: ${userId}")
        binding.edtDate.setOnClickListener {
            val currentDate = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                requireContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    if (selectedDate.after(currentDate)) {
                        Toast.makeText(requireContext(), "Chọn ngày không được lớn hơn ngày hiện tại", Toast.LENGTH_SHORT).show()
                    } else {
                        val date: String = formatDate.format(selectedDate.time)
                        binding.edtDate.text = date
                    }
                },
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }
        return binding.root
    }

    private fun setUpObserverKhachHang() {
        binding.btnAdd.setOnClickListener {
            val tenKH = binding.edtName.text.toString()
            val sdtKH = binding.edtPhone.text.toString()
            val ngaySinhKH = binding.edtDate.text.toString()

            // Lấy giới tính khách hàng từ RadioButton
            val selectedGenderId = binding.radGroupGender.checkedRadioButtonId
            var gioiTinhKH = ""
            when (selectedGenderId) {
                R.id.radNam -> gioiTinhKH = "Nam"
                R.id.radNu -> gioiTinhKH = "Nữ"
            }

            val congviecKH = binding.edtWork.text.toString()
            val diaChiKH = binding.edtAddress.text.toString()
            val emailKH = binding.edtEmail.text.toString()

            sharedViewModel.getUserId().observe(viewLifecycleOwner, Observer { userId ->
                Log.d("IdTK", "User ID in HomeFragment: $userId")

                // Kiểm tra điều kiện trước khi gửi dữ liệu lên server
                // ...

                viewModel.postCustomers(userId, tenKH, sdtKH, ngaySinhKH, gioiTinhKH, congviecKH, diaChiKH, emailKH)
                    .observe(viewLifecycleOwner) { data ->
                        when (data.status) {
                            DataResult.Status.SUCCESS -> {
                                // Xử lý khi thêm khách hàng thành công
                                val addedCustomer = data.data?.body() as CustomerResponse
                                Toast.makeText(requireContext(), "Thêm khách hàng thành công", Toast.LENGTH_SHORT).show()
                                findNavController().navigateUp()

                                // Điều gì đó sau khi thêm khách hàng thành công
                            }

                            DataResult.Status.LOADING -> {
                                // Xử lý trạng thái đang tải
                                Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                            }

                            DataResult.Status.ERROR -> {
                                // Xử lý trạng thái lỗi
                                Toast.makeText(requireContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            })
        }
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
        binding.edtName.setText(customer.tenKH)
        binding.edtPhone.setText(customer.sdtKH)
        binding.edtDate.setText(customer.ngaySinhKH)
        when (customer.gioiTinhKH) {
            "Nam" -> binding.radNam.isChecked = true
            "Nữ" -> binding.radNu.isChecked = true
        }
        binding.radGroupGender.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radNam -> {
                    // Cập nhật giới tính của khách hàng thành "Nam"
                    customer.gioiTinhKH = "Nam"
                }
                R.id.radNu -> {
                    // Cập nhật giới tính của khách hàng thành "Nữ"
                    customer.gioiTinhKH = "Nữ"
                }
            }
        }
        binding.edtEmail.setText(customer.emailKH)
        binding.edtAddress.setText(customer.diaChiKH)
        binding.edtWork.setText(customer.congviecKH)

    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}