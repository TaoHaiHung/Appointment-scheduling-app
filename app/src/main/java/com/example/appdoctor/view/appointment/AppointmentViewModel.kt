package com.example.appdoctor.view.appointment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appdoctor.repository.Repository
import com.example.appdoctor.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    fun getPhieuKhamByIdTK(IdTK: String,tenKH:String) = liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.getPhieuKhamByIdTK(IdTK,tenKH)))

        } catch (e: Exception) {
            // Xảy ra lỗi
            Log.e("Okhttp", e.toString())
            emit(DataResult.error(e.message ?: "Lỗi Data"))
        }
    }
    fun deletePhieuKham(idPK:String, tinhtrang:String)= liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.deletePhieuKham(idPK,tinhtrang)))

        } catch (e: Exception) {
            // Xảy ra lỗi
            Log.e("Okhttp", e.toString())
            emit(DataResult.error(e.message ?: "Lỗi Data"))
        }
    }
}