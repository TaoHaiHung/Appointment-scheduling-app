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
class SignUpViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    fun register(username: String, email:String,pass: String) = liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.register(username,email, pass)))

        } catch (e: Exception) {
            // Xảy ra lỗi
            Log.e("Okhttp", e.toString())
            emit(DataResult.error(e.message ?: "Lỗi Data"))
        }
    }
}