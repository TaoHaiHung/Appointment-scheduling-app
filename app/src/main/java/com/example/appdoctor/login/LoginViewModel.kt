package com.example.appdoctor.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appdoctor.repository.Repository
import com.example.appdoctor.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository):ViewModel(){
    fun login(username: String, pass: String) = liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.login(username,pass)))

        } catch (e: Exception) {
            // Xảy ra lỗi
            Log.e("Okhttp", e.toString())
            emit(DataResult.error(e.message ?: "Lỗi Data"))
        }
    }




}