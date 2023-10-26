package com.example.appdoctor.view.appointment

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.appdoctor.MainActivity
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentDetailAppointmentBinding
import com.example.appdoctor.model.PhieuKham
import com.example.appdoctor.model.PhieuKhamResponse
import com.example.appdoctor.utils.Constant
import com.example.appdoctor.utils.DataResult
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAppointmentFragment :Fragment() {
    private lateinit var binding:FragmentDetailAppointmentBinding
    private val viewModel: AppointmentViewModel by viewModels()
    private var phieukham = PhieuKham()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).handleShowBottomNavigation(false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_appointment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel=viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<PhieuKham>(Constant.KEY_PK)?.let {
            phieukham=it
            setUpView(phieukham)
        }
        handlebtnDelete()
        handleBtnDetail()

    }

    private fun handleBtnDetail() {
        binding.btnDetail.setOnClickListener {
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
            var llOption=view.findViewById<LinearLayout>(R.id.llOption)
            llOption.visibility=View.GONE
            id.text=phieukham.benhnhan?.idKH
            name.text=phieukham.benhnhan?.tenKH
            phone.text=phieukham.benhnhan?.sdtKH
            date.text=phieukham.benhnhan?.ngaySinhKH
            sex.text=phieukham.benhnhan?.gioiTinhKH
            address.text=phieukham.benhnhan?.diaChiKH
            work.text=phieukham.benhnhan?.congviecKH
            email.text = if (phieukham.benhnhan?.emailKH.isNullOrEmpty()) "Chưa cập nhật" else phieukham.benhnhan?.emailKH
            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
        }
    }

    private fun handlebtnDelete() {
        binding.btnDelete.setOnClickListener {
            val dialog = Dialog(requireContext())
            var view= layoutInflater.inflate(R.layout.dialog_cancel_appointment,null)
            var btnCancel = view.findViewById<AppCompatButton>(R.id.btnCancel)
            var btnOk = view.findViewById<AppCompatButton>(R.id.btnOk)
            dialog.setContentView(view)
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnOk.setOnClickListener {
                setUpDelete()
                dialog.dismiss()
                requireActivity().onBackPressed()
            }
            dialog.setOnShowListener {
                val width = ViewGroup.LayoutParams.MATCH_PARENT
                val height = ViewGroup.LayoutParams.WRAP_CONTENT
                dialog.window?.setLayout(width, height)
            }
            dialog.setCancelable(true)
//           dialog.setContentView(view)
            dialog.show()

        }
    }

    private fun setUpDelete() {
        viewModel.deletePhieuKham(binding.tvIdPK.text.toString(),"Đã hủy").observe(viewLifecycleOwner){ data->
            when (data.status) {
                DataResult.Status.SUCCESS -> {
                    val listPhieuKham: ArrayList<PhieuKham> = ArrayList()
                    val value = data.data?.body() as PhieuKhamResponse
                    requireActivity().onBackPressed()
                    value.listPhieuKham?.forEach {
                        listPhieuKham.add(it)
                        Log.d("PhieuKham", listPhieuKham.toString())

                    }
                }

                DataResult.Status.LOADING -> {

                }

                DataResult.Status.ERROR -> {


                }
            }

        }
    }

    private fun setUpView(phieukham: PhieuKham) {
        when{
            phieukham.IdBS?.isNotEmpty() == true ->{
                Glide.with(binding.ivImage.context).load(phieukham.doctor?.image).into(binding.ivImage)
                binding.tvNameDoctor.text=phieukham.doctor?.TenBacSi
                binding.tvAddressOrDep.text=phieukham.doctor?.chuyenkhoa
                binding.tvTitleAddressOrDep.text="Chuyên khoa"
            }
            phieukham.idCLinic?.isNotEmpty()==true->{
                Glide.with(binding.ivImage.context).load(phieukham.clinic?.image).into(binding.ivImage)
                binding.tvNameDoctor.text=phieukham.clinic?.TenPhongKham
                binding.tvAddressOrDep.text=phieukham.diaChi
                binding.tvTitleAddressOrDep.text="Địa chỉ"
            }
        }
        binding.tvIdPK.text=phieukham.idPK
        binding.tvDateBooking.text=phieukham.ngayKham
        binding.tvHour.text=phieukham.gioKham
        binding.tvStt.text=phieukham.stt
        binding.tvIdCustomer.text=phieukham.benhnhan?.idKH
        binding.tvNameCustomer.text=phieukham.benhnhan?.tenKH
        binding.tvDate.text=phieukham.benhnhan?.ngaySinhKH
        when (phieukham.tinhTrang) {
            "Đã đăng ký" -> binding.tvStatus.setTextColor(Color.GREEN)
            "Đã hủy" -> {
                binding.tvStatus.setTextColor(Color.RED)
                binding.btnDelete.visibility=View.GONE
                binding.btnReset.visibility=View.VISIBLE
                binding.btnReset.setOnClickListener {
                    when{
                        phieukham.IdBS?.isNotEmpty() == true ->{
                            findNavController().navigate(R.id.action_detailAppointmentFragment_to_detailDotorFragment ,
                                bundleOf(Constant.KEY_PK to phieukham)
                            )
                        }
                        phieukham.idCLinic?.isNotEmpty()==true->{
                            findNavController().navigate(R.id.action_detailAppointmentFragment_to_detailClinicFragment ,
                                bundleOf(Constant.KEY_PK to phieukham)
                            )
                        }

                    }

                }
            }
            else -> binding.tvStatus.setTextColor(Color.BLACK) // Màu mặc định nếu không khớp
        }
        binding.tvStatus.text=phieukham.tinhTrang


    }
}