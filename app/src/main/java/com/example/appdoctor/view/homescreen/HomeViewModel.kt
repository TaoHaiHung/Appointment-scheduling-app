package com.example.appdoctor.view.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appdoctor.repository.Repository
import com.example.appdoctor.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    // Bác sĩ
    fun getListBacSi()= liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.getListBacSi()))
        }
        catch (e:Exception){
            Log.e("Okhttp", e.toString())
            emit(DataResult.error(e.message?:"Error Data"))

        }
    }
    // Chuyên Khoa
    fun getListChuyenKhoa()= liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.getListChuyenKhoa()))
        }
        catch (e:Exception){
            Log.e("Okhttp", e.toString())
            emit(DataResult.error(e.message?:"Error Data"))

        }
    }

    // Bệnh viện
    fun getListBenhVien()= liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.getListBenhVien()))
        }
        catch (e:Exception){
            Log.e("Okhttp", e.toString())
            emit(DataResult.error(e.message?:"Error Data"))

        }
    }
    // Phòng khám
    fun getListPhongKham()= liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.getListPhongKham()))
        }
        catch (e:Exception){
            Log.e("Okhttp", e.toString())
            emit(DataResult.error(e.message?:"Error Data"))

        }
    }

    fun getCustomersByUserId(idTK:String)= liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.getCustomersByUserId(idTK)))
        }
        catch (e:Exception){
            Log.e("Okhttp", e.toString())
            emit(DataResult.error(e.message?:"Error Data"))

        }
    }


}