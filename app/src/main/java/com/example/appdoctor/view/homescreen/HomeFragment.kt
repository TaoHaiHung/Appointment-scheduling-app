package com.example.appdoctor.view.homescreen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdoctor.MainActivity
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentHomeBinding
import com.example.appdoctor.model.Clinic
import com.example.appdoctor.model.ClinicResponse
import com.example.appdoctor.model.Doctor
import com.example.appdoctor.model.DoctorResponse
import com.example.appdoctor.model.Hospital
import com.example.appdoctor.model.HospitalResponse
import com.example.appdoctor.model.Specialty
import com.example.appdoctor.model.SpecialtyResponse
import com.example.appdoctor.model.User
import com.example.appdoctor.utils.Constant
import com.example.appdoctor.utils.DataResult
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment:Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    lateinit var fragment: Fragment
    private var hosptialAdapter: HospitalAdapter?=null
    private var cliniclAdapter: ClinicAdapter?=null
    private lateinit var chuyenKhoaAdapter: SpecialtyAdapter
    private lateinit var doctorAdapter: DoctorAdapter
    private var user = User()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).handleShowBottomNavigation(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Kiểm tra trạng thái đăng nhập từ SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("loggedIn", false)
        val idTK = sharedPreferences.getString("idTK", "")
        Log.d("TaiKhoanHome", idTK.toString())
        if (!isLoggedIn) {
            // Người dùng chưa đăng nhập, điều hướng tới màn hình đăng nhập
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        } else {
            // Đã đăng nhập, tiếp tục khởi tạo giao diện và xử lý dữ liệu
            initView()
            arguments?.getParcelable<User>(Constant.KEY_USER)?.let {
                user = it
                setUpView(user)
            }

            setUpRecyclerviewDoctor()
            setUpObserverDoctor()
            setUpRecyclerviewSpecialty()
            setUpObserverSpecialty()
            setUpRecyclerviewHospital()
            setUpObserverHospital()
            setUpRecyclerviewClinic()
            setUpObserverClinic()
        }
    }

    private fun setUpObserverClinic() {
        viewModel.getListPhongKham().observe(viewLifecycleOwner) { data ->
            when (data.status) {
                DataResult.Status.SUCCESS -> {
                    showShimmer(false)
                    val listClinic: ArrayList<Clinic> = ArrayList()
                    val value = data.data?.body() as ClinicResponse
                    value.listClinic?.forEach {
                        listClinic.add(it)
                        Log.d("setUpObserver", listClinic.toString())

                    }
                    cliniclAdapter?.submitList(listClinic)
                    cliniclAdapter?.onItemClick ={clinic->
                        findNavController().navigate(R.id.action_homeFragment_to_detailClinicFragment , bundleOf(
                            Constant.KEY_CLINIC to clinic)
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

    private fun setUpRecyclerviewClinic() {
        cliniclAdapter = ClinicAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvClinic.apply {
            adapter = cliniclAdapter
            setLayoutManager(layoutManager)
        }
    }


    private fun setUpObserverHospital() {
        viewModel.getListBenhVien().observe(viewLifecycleOwner) { data ->
            when (data.status) {
                DataResult.Status.SUCCESS -> {
                    showShimmer(false)
                    val listHospital: ArrayList<Hospital> = ArrayList()
                    val value = data.data?.body() as HospitalResponse
                    value.listHospital?.forEach {
                        listHospital.add(it)
                        Log.d("setUpObserver", listHospital.toString())

                    }
                    hosptialAdapter?.submitList(listHospital)
                    hosptialAdapter?.onItemClick ={hospital->
//                        findNavController().navigate(R.id.action_homeFragment_to_detailClinicFragment , bundleOf(
//                            Constant.KEY_HOSPITAL to hospital)
//                        )
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

    private fun setUpRecyclerviewHospital() {
        hosptialAdapter = HospitalAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHospital.apply {
            adapter = hosptialAdapter
            setLayoutManager(layoutManager)
        }
    }

    private fun setUpObserverSpecialty() {
        viewModel.getListChuyenKhoa().observe(viewLifecycleOwner) { data ->
            when (data.status) {
                DataResult.Status.SUCCESS -> {
//                    showShimmer(false)
                    val listSpecialty: ArrayList<Specialty> = ArrayList()
                    val value = data.data?.body() as SpecialtyResponse
                    value.listSpecialty?.forEach {
                        listSpecialty.add(it)
                    }
                    var listSix =listSpecialty.take(6)
                    chuyenKhoaAdapter.submitList(listSix)
                    binding.btXemThem.setOnClickListener {
                        val dialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
                var view= layoutInflater.inflate(R.layout.bottom_dialog_chuyenkhoa, null)
                // handleView
                var rvChuyenKhoa = view.findViewById<RecyclerView>(R.id.rvChuyenKhoa)
                rvChuyenKhoa.apply{
                    var layoutParams =GridLayoutManager(requireContext(),4)
                    layoutManager=layoutParams
                    chuyenKhoaAdapter= SpecialtyAdapter()
                    adapter=chuyenKhoaAdapter
                    chuyenKhoaAdapter.submitList(listSpecialty)

                }
                dialog.setCancelable(true)
                dialog.setContentView(view)
                dialog.show()


                    }
                    chuyenKhoaAdapter.onItemClick ={chuyenkhoa->
                    }

                }

                DataResult.Status.LOADING -> {

                }

                DataResult.Status.ERROR -> {


                }
            }
        }
    }



    private fun setUpRecyclerviewSpecialty() {
        chuyenKhoaAdapter= SpecialtyAdapter()
        val layoutManager=
            GridLayoutManager(requireContext(),3)
        binding.rvChuyenKhoa.apply {
            adapter=chuyenKhoaAdapter
            setLayoutManager(layoutManager)
        }
    }

    private fun setUpRecyclerviewDoctor() {
        doctorAdapter = DoctorAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvDoctor.apply {
            adapter = doctorAdapter
            setLayoutManager(layoutManager)
        }
    }

    private fun setUpObserverDoctor() {
        viewModel.getListBacSi().observe(viewLifecycleOwner) { data ->
            when (data.status) {
                DataResult.Status.SUCCESS -> {
                    showShimmer(false)
                    val listDoctor: ArrayList<Doctor> = ArrayList()
                    val value = data.data?.body() as DoctorResponse
                    value.listDoctor?.forEach {
                        listDoctor.add(it)
                        Log.d("setUpObserver", listDoctor.toString())
                    }
                    doctorAdapter.submitList(listDoctor)
                    doctorAdapter.onItemClick ={doctor->
                        findNavController().navigate(R.id.action_homeFragment_to_detailDotorFragment , bundleOf(
                            Constant.KEY_DOCTOR to doctor)
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

    private fun showShimmer(isShow: Boolean) {

        if(isShow){
            binding.shimmer.visibility=View.VISIBLE
            binding.rvDoctor.visibility=View.GONE
            binding.shimmer.startShimmer()

            //Hospital
            binding.shimmerHospital.visibility=View.VISIBLE
            binding.rvHospital.visibility=View.GONE
            binding.shimmerHospital.startShimmer()

            //Clinic
            binding.shimmerClinic.visibility=View.VISIBLE
            binding.rvClinic.visibility=View.GONE
            binding.shimmerClinic.startShimmer()

        }else{
            binding.shimmer.visibility=View.GONE
            binding.rvDoctor.visibility=View.VISIBLE
            binding.shimmer.stopShimmer()

            //Hospital
            binding.shimmerHospital.visibility=View.GONE
            binding.rvHospital.visibility=View.VISIBLE
            binding.shimmerHospital.stopShimmer()

            //Clinic
            binding.shimmerClinic.visibility=View.GONE
            binding.rvClinic.visibility=View.VISIBLE
            binding.shimmerClinic.stopShimmer()

        }

    }





    private fun setUpView(user: User) {
        binding.tvName.text=user.username



    }







    private fun initView() {
        binding.tvSearch.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_findFragment)
        }
        binding.llBacSi.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_findFragment)
            var view= layoutInflater.inflate(R.layout.fragment_find_doctor, null)
            // handleView
            var tvLuaChon = view.findViewById<TextView>(R.id.tvLuaChon)
            tvLuaChon.text="Bác sĩ"

        }

    }
}