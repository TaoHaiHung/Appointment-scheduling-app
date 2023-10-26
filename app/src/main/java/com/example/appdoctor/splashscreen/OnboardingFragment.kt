package com.example.appdoctor.splashscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.appdoctor.MainActivity
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentOnboardingBinding
import com.example.appdoctor.login.LoginFragment


class OnboardingFragment :Fragment() {
    lateinit var binding: FragmentOnboardingBinding
    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlide(
                "Đặt lịch khám dễ dàng",
                "An tâm đặt khám - Thao tác nhanh chóng - Sử dụng tiện lợi",
                R.drawable.img_slider_one
            ),
            IntroSlide(
                "An tâm đặt khám",
                "Lịch khám được bác sĩ xác nhận vá sắp xếp thời gian",
                R.drawable.img_slider_two
            ),
            IntroSlide(
                "Thao tác nhanh chóng ",
                "Đặt khám mọi lúc mọi nơi",
                R.drawable.img_slider_three
            ),
            IntroSlide(
                "Sử dụng tiện lợi",
                "Dễ dàng theo dõi lịch sử khám Giao diện thân thiện người dùng",
                R.drawable.img_slider_four
            ),
            IntroSlide(
                "Tin y tế chính thống",
                "Kiến thức sức khỏe ,bệnh, thuốc 100% được biên soạn bởi bác sĩ, dược sĩ",
                R.drawable.img_slider_fine
            )

        )
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).handleShowBottomNavigation(false)
        binding= FragmentOnboardingBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleDataSlider()
        handleButtonNext()
        handleTextSkipIntro()




    }

    private fun handleTextSkipIntro() {
        binding.textSkipIntro.setOnClickListener {
            val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getBoolean("loggedIn", false)
            if(isLoggedIn){
                // Đã đăng nhập trước đó
                val idTK = sharedPreferences.getString("idTK", "")
                Log.d("TaiKhoanOn", idTK.toString())
                findNavController().navigate(R.id.action_onboardingFragment_to_homeFragment)
            }
            else{
                // Chưa đăng nhập trước đó
                findNavController().navigate(R.id.action_onboardingFragment_to_loginFragment)

            }

        }
    }

    private fun handleDataSlider() {
        binding.introSliderViewPager.adapter = introSliderAdapter
        setupIndicators()
        setCurrentIndicator(0)
        binding.introSliderViewPager.registerOnPageChangeCallback(object :
        ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
    }

    private fun setCurrentIndicator(index: Int) {
        val chilCount = binding.indicatorContainer.childCount
        for (i in 0 until chilCount) {
            val imageView = binding.indicatorContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_inactive
                    )
                )
            }
        }

    }

    private fun setupIndicators() {
        val indicators= arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams :LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext())
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_inactive
                    )

                )
                this?.layoutParams = layoutParams
            }

            binding.indicatorContainer.addView(indicators[i])
        }
    }

    private fun handleButtonNext() {
        binding.buttonNext.setOnClickListener{
            val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getBoolean("loggedIn", false)
            if(binding.introSliderViewPager.currentItem+1< introSliderAdapter.itemCount){
                binding.introSliderViewPager.currentItem+=1
            } else if(isLoggedIn){
                // Đã đăng nhập trước đó
                val idTK = sharedPreferences.getString("idTK", "")
                Log.d("TaiKhoanOn", idTK.toString())
                findNavController().navigate(R.id.action_onboardingFragment_to_homeFragment)
                }
            else{
                // Chưa đăng nhập trước đó
                findNavController().navigate(R.id.action_onboardingFragment_to_loginFragment)

            }

            }
        }
    }



