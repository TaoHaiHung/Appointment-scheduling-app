package com.example.appdoctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.appdoctor.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var binding :ActivityMainBinding
    private var navController : NavController?=null
    lateinit var fragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setUpNavigationFragment()
        initView()

    }

    private fun initView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home ->{
                findNavController(R.id.fragmentView).navigate(R.id.homeFragment)

            }
            R.id.Calendar ->{
                findNavController(R.id.fragmentView).navigate(R.id.appointmentFragment)



            }
            R.id.File ->{
                findNavController(R.id.fragmentView).navigate(R.id.fileFragment)


            }
            R.id.User ->{
                findNavController(R.id.fragmentView).navigate(R.id.userFragment)

            }
        }
        return true
    }


    fun handleShowBottomNavigation(isShow: Boolean) {
        binding.bottomNavigationView.visibility=
            if(isShow)
                View.VISIBLE
            else View.GONE
     }


    private fun setUpNavigationFragment() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentView) as NavHostFragment
        navController = navHostFragment.navController
    }


    }

