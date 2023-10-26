package com.example.appdoctor.view.booking

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appdoctor.R
import com.example.appdoctor.databinding.FragmentCalendarBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
@AndroidEntryPoint
class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private var mCalendarView: CalendarView? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCalendarView = binding.calendarView

        val currentDate = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

        mCalendarView?.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            val selectedDate = sdf.parse("$year/${month + 1}/$dayOfMonth")
            val calendar = Calendar.getInstance()
            calendar.time = selectedDate

            // Check if selected date is a Saturday or Sunday
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                if (selectedDate.before(currentDate)) {
                    Log.d(TAG, "Selected date is a Saturday or Sunday in the past")
                    return@setOnDateChangeListener
                } else {
                    // Do nothing, don't allow click for Saturday and Sunday
                    return@setOnDateChangeListener
                }
            }

            if (selectedDate.before(currentDate)) {
                Log.d(TAG, "Selected date is before current date")
                return@setOnDateChangeListener
            } else {
//                val date = "$year/${month + 1}/$dayOfMonth"
                val date ="$dayOfMonth/${month+1}/$year"
                Log.d("123", "onSelectedDayChange: yyyy/mm/dd:$date")
                val sharedPreferences = requireActivity().getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("date", date)
                editor.apply()
                requireActivity().onBackPressed()
//                val fragment = BkFragment()
//                val args = Bundle()
//                args.putString("date", date)
//                fragment.arguments = args
//                findNavController().navigate(R.id.action_calendarFragment_to_bookingFragment, args)
            }
        }

        mCalendarView?.minDate = Calendar.getInstance().timeInMillis
    }

    companion object {
        private const val TAG = "CalendarFragment"
    }
}