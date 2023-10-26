package com.example.appdoctor.view.booking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appdoctor.model.Clinic
import com.example.appdoctor.repository.Repository
import com.example.appdoctor.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
@HiltViewModel
class BookingViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    fun getScheduleByDoctor(IdBS :String)= liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.getScheduleByDoctor(IdBS)))
        }
        catch (e:Exception){
            Log.e("Okhttp", e.toString())
            emit(DataResult.error(e.message?:"Error Data"))

        }
    }
    fun getScheduleByClinic(idClinic: String)= liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.getScheduleByClinic(idClinic)))
        }
        catch (e:Exception){
            Log.e("Okhttp", e.toString())
            emit(DataResult.error(e.message?:"Error Data"))

        }
    }
    fun postPhieuKham(IdTK: String,IdBS: String,idKH:String,NgayKham:String,GioKham:String,tinhtrang:String,Note:String)=
        liveData(Dispatchers.IO) {
            emit(DataResult.loading())
            try {
                emit(DataResult.success(repository.postPhieuKham(IdTK, IdBS , idKH, NgayKham , GioKham, tinhtrang, Note)))
            }
            catch (e:Exception){
                Log.e("Okhttp", e.toString())
                emit(DataResult.error(e.message?:"Error Data"))

            }
        }
    fun postPhieuKhamByClinic(IdTK: String,IdPhongKham: String,idKH:String,diaChi:String,NgayKham:String,GioKham:String,tinhtrang:String,Note:String)=
        liveData(Dispatchers.IO) {
            emit(DataResult.loading())
            try {
                emit(DataResult.success(repository.postPhieuKhamByClinic(IdTK, IdPhongKham , idKH, diaChi,NgayKham , GioKham, tinhtrang, Note)))
            }
            catch (e:Exception){
                Log.e("Okhttp", e.toString())
                emit(DataResult.error(e.message?:"Error Data"))

            }
        }
}