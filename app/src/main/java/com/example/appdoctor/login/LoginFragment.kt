package com.example.appdoctor.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appdoctor.MainActivity
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentLoginBinding
import com.example.appdoctor.model.User
import com.example.appdoctor.model.UserResponse
import com.example.appdoctor.utils.Constant
import com.example.appdoctor.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment:Fragment() {
    lateinit var binding :FragmentLoginBinding
    private lateinit var sharedViewModel: SharedViewModel
    private val viewModel: LoginViewModel by viewModels()
    private var user = User()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).handleShowBottomNavigation(false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setUpObserverLogin()
        return binding.root
    }

    private fun setUpObserverLogin() {
        binding.btLogin.setOnClickListener {
            val username = binding.edtEmail.text.toString()
            val pass = binding.edtPass.text.toString()
            viewModel.login(username, pass).observe(viewLifecycleOwner) { data ->
                when (data.status) {
                    DataResult.Status.SUCCESS -> {
                        val listUser :ArrayList<User> = ArrayList()
                        val value = data.data?.body() as UserResponse
                        value.listUser?.forEach {
                            listUser.add(it)
                            for (user in listUser) {
                                sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
                                val idTK = user.IdTK // Thay "your_user_id" bằng ID tài khoản thực tế
                                sharedViewModel.setUserId(idTK?:"")
                                if (user.username == username && user.pass == pass) {
                                    Toast.makeText(requireContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                                    // Lưu trạng thái đăng nhập thành công
                                    val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                                    val editor = sharedPreferences.edit()
                                    editor.putBoolean("loggedIn", true)
                                    editor.putString("idTK", user.IdTK)
                                    editor.apply()
                                    Log.d("Account", listUser.toString())
                                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment,
                                        bundleOf(Constant.KEY_USER to user)
                                    )
                                    return@observe
                                }
                            }
                            // Nếu không có người dùng nào trùng khớp, đăng nhập thất bại
                            Toast.makeText(requireContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                        }
                    }
                    DataResult.Status.ERROR -> {
                        Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                    }
                    DataResult.Status.LOADING -> {
                        Toast.makeText(requireContext(), "LOADING", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpListener()
    }

    private fun setUpListener() {

    }

    private fun initView() {
        binding.tvSingup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_singUpFragment)
        }


    }
}