package com.example.appdoctor.view.file

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appdoctor.repository.Repository
import com.example.appdoctor.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
@HiltViewModel
class NewFileViewModel @Inject constructor(private val repository: Repository): ViewModel(){
    fun  postCustomers(IdTK: String,tenKH:String,sdtKH:String,ngaySinhKH:String,gioiTinhKH:String,
                       congviecKH:String,diaChiKH:String,emailKH: String) = liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.postCustomers(IdTK, tenKH, sdtKH, ngaySinhKH, gioiTinhKH, congviecKH, diaChiKH, emailKH)))

        } catch (e: Exception) {
            // Xảy ra lỗi
            Log.e("Okhttp", e.toString())
            emit(DataResult.error(e.message ?: "Lỗi Data"))
        }
    }
}