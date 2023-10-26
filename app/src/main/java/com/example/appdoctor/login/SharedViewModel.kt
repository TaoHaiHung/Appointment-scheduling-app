package com.example.appdoctor.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appdoctor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class SharedViewModel:ViewModel() {
    private val userIdLiveData = MutableLiveData<String>()

    fun setUserId(idTK: String) {
        userIdLiveData.value = idTK
    }

    fun getUserId(): LiveData<String> {
        return userIdLiveData
    }
}