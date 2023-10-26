package com.example.appdoctor.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appdoctor.MainActivity
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentSplashBinding


class SplashFragment : Fragment(){
    lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).handleShowBottomNavigation(false)
        binding= FragmentSplashBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
//        binding.btnClick1.setOnClickListener{
//            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
//
//        }
    }

    private fun setUpListener() {
        Handler(Looper.getMainLooper()).postDelayed({
             findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
        },3000)

    }
    }