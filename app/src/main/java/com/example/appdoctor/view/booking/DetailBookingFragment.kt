package com.example.appdoctor.view.booking

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentDetailBookingBinding
import com.example.appdoctor.model.Clinic
import com.example.appdoctor.model.Customer
import com.example.appdoctor.model.Doctor
import com.example.appdoctor.model.PhieuKham

import com.example.appdoctor.model.PhieuKhamResponse
import com.example.appdoctor.utils.Constant
import com.example.appdoctor.utils.DataResult
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailBookingFragment :Fragment(){
    private lateinit var binding: FragmentDetailBookingBinding
    private val viewModel: BookingViewModel by viewModels()
    private var doctor = Doctor()
    private var clinic = Clinic()
    private var phieuKham=PhieuKham()
    private var customer = Customer()
    private var idBS :String =""
    private var idClinic :String =""
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_booking, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        sharedPreferences = requireActivity().getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
        setUpObserverPostData()
            return binding.root
    }



    private fun setUpObserverPostData() {
        binding.btnConfirm.setOnClickListener {
            // Thông tin bệnh nhân
            val idKH = sharedPreferences.getString("idKH", null).toString()
            // Thông tin đặt khám
            val ngayKham = binding.tvDateBooking.text.toString()
            val gioKham = binding.tvHour.text.toString()
//            val IdBS = sharedPreferences.getString("idBS", "").toString()
//            val idClinic =sharedPreferences.getString("idClinic","").toString()
            val address=sharedPreferences.getString("address", null).toString()
            val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val idTK = sharedPreferences.getString("idTK", null).toString()
            Log.d("123", idClinic)
            when{
                idBS.isNotEmpty()->{
                    viewModel.postPhieuKham(idTK, idBS, idKH, ngayKham, gioKham, "Đã đăng ký", "").observe(viewLifecycleOwner) { data ->
                        when (data.status) {
                            DataResult.Status.SUCCESS -> {
                                // Xử lý khi Đặt khám thành công
                                val value = data.data?.body() as PhieuKhamResponse
                                val bundle = Bundle().apply {
                                    putParcelableArrayList("listPhieuKham", ArrayList(value.listPhieuKham))
                                }
                                Log.d("PhieuKham", value.listPhieuKham.toString())
                                findNavController().navigate(
                                    R.id.action_detailBookingFragment_to_successDetailFragment,
                                    bundle
                                )
                                // Điều gì đó sau khi thêm phiếu khám thành công
                            }
                            DataResult.Status.LOADING -> {
                                Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                            }
                            DataResult.Status.ERROR -> {
                                // Xử lý trạng thái lỗi
                                Toast.makeText(requireContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                idClinic.isNotEmpty()->{
                    viewModel.postPhieuKhamByClinic(idTK, idClinic, idKH,address, ngayKham, gioKham, "Đã đăng ký", "").observe(viewLifecycleOwner) { data ->
                        when (data.status) {
                            DataResult.Status.SUCCESS -> {
                                // Xử lý khi Đặt khám thành công
                                val value = data.data?.body() as PhieuKhamResponse
                                val bundle = Bundle().apply {
                                    putParcelableArrayList("listPhieuKham", ArrayList(value.listPhieuKham))
                                }
                                Log.d("PhieuKham", value.listPhieuKham.toString())
                                findNavController().navigate(
                                    R.id.action_detailBookingFragment_to_successDetailFragment,
                                    bundle
                                )
                                // Điều gì đó sau khi thêm phiếu khám thành công
                            }
                            DataResult.Status.LOADING -> {
                                Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                            }
                            DataResult.Status.ERROR -> {
                                // Xử lý trạng thái lỗi
                                Toast.makeText(requireContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }

        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Doctor>(Constant.KEY_DOCTOR)?.let {
            doctor=it
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
        initData()
    }

    private fun setUpViewByPhieuKham(phieuKham: PhieuKham) {
        when{
            phieuKham.doctor?.idBacSi?.isNotEmpty()==true ->{
                idBS=phieuKham.doctor.idBacSi
                binding.tvDoctorOrClinic.text="Bác sĩ"
                binding.tvTitleAddress.text="Chuyên khoa"
                binding.tvAddress.text=phieuKham.doctor?.chuyenkhoa
                binding.tvNameDoctor.text = phieuKham.doctor?.TenBacSi
            }
            phieuKham.clinic?.idPhongKham?.isNotEmpty()==true ->{
                idClinic=phieuKham.clinic.idPhongKham
                binding.tvNameDoctor.text = phieuKham.clinic.TenPhongKham
                binding.tvDoctorOrClinic.text="Phòng khám"
                binding.tvTitleAddress.text="Địa chỉ"
                binding.tvAddress.text=sharedPreferences.getString("address", null)
            }

        }
    }

    private fun setUpViewClinic(clinic: Clinic) {

        if(clinic.idPhongKham?.isNotEmpty() == true){
            idClinic=clinic.idPhongKham
            binding.tvNameDoctor.text = clinic.TenPhongKham
            binding.tvDoctorOrClinic.text="Phòng khám"
            binding.tvTitleAddress.text="Địa chỉ"
            binding.tvAddress.text=sharedPreferences.getString("address", null)
        }
        else{
            setUpViewByPhieuKham(phieuKham)
        }

    }


    private fun handleDetailCustomer() {
        binding.ivDetailCustomer.setOnClickListener {
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
            var llOption=view.findViewById<LinearLayout>(R.id.llOption)
            llOption.visibility=View.GONE
            id.text=sharedPreferences.getString("idKH",null)
            name.text=sharedPreferences.getString("tenKH",null)
            phone.text=sharedPreferences.getString("sdt","Chưa cập nhật")?:"Chưa cập nhật"
            date.text=sharedPreferences.getString("birthday",null)
            sex.text=sharedPreferences.getString("gender",null)
            address.text=sharedPreferences.getString("Address","Chưa cập nhật")?:"Chưa cập nhật"
            work.text=sharedPreferences.getString("work","Chưa cập nhật")?:"Chưa cập nhật"
            email.text = sharedPreferences.getString("email","Chưa cập nhật")?:"Chưa cập nhật"
            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
        }
    }

    private fun setUpView(doctor: Doctor) {
        if (doctor.idBacSi?.isNotEmpty()==true){
            idBS=doctor.idBacSi
            binding.tvDoctorOrClinic.text="Bác sĩ"
            binding.tvTitleAddress.text="Chuyên khoa"
            binding.tvAddress.text=doctor.chuyenkhoa
            binding.tvNameDoctor.text = doctor.TenBacSi
        }
        else{
            setUpViewByPhieuKham(phieuKham)
        }




    }

    private fun initData() {
//        binding.tvDate.text = arguments?.getString("date")
        binding.tvDateBooking.text= sharedPreferences.getString("date",null)
        binding.tvNameCustomer.text= sharedPreferences.getString("tenKH",null)
        binding.tvDate.text=sharedPreferences.getString("birthday",null)
        binding.tvHour.text=sharedPreferences.getString("hour",null)
        handleDetailCustomer()
    }

}