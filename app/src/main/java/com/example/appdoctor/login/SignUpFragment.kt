package com.example.appdoctor.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.appdoctor.MainActivity
import com.example.appdoctor.R

import com.example.appdoctor.databinding.FragmentSignUpBinding
import com.example.appdoctor.model.User
import com.example.appdoctor.model.UserResponse
import com.example.appdoctor.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment :Fragment() {
    lateinit var binding : FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).handleShowBottomNavigation(false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setUpObserverRegister()
        return binding.root
    }

    private fun setUpObserverRegister() {
        binding.btnRegister.setOnClickListener {
            val username = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPass.text.toString()

            viewModel.register(username, email, pass).observe(viewLifecycleOwner) { data ->
                when (data.status) {
                    DataResult.Status.SUCCESS -> {
                        val listUser :ArrayList<User> = ArrayList()
                        val value = data.data?.body() as UserResponse
                        value.listUser?.forEach {
                            listUser.add(it)
                            for (user in listUser) {
                                if (user.username == username || user.email== email ) {
                                    Toast.makeText(requireContext(), "Tên đăng nhập và email trùng", Toast.LENGTH_SHORT).show()
                                    return@observe
                                }
//                                else if(username.isEmpty() || email.isEmpty() || pass.isEmpty()){
//                                    Toast.makeText(requireContext(),"Nhập thiếu thông tin ",Toast.LENGTH_SHORT).show()
//                                }
                                else{

                                }
                            }



                        }
                        Toast.makeText(requireContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show()

                    }
                    DataResult.Status.ERROR -> {
                        Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show()
                    }
                    DataResult.Status.LOADING -> {
                        Toast.makeText(requireContext(), "Đang đăng ký...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}