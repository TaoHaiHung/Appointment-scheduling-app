package com.example.appdoctor.view.bookingClinic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appdoctor.repository.Repository
import com.example.appdoctor.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class BookingClinicViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    fun getAddressByClinic(idClinic:String)= liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.getAddressByClinic(idClinic)))
        }
        catch (e:Exception){
            Log.e("Okhttp", e.toString())
            emit(DataResult.error(e.message?:"Error Data"))

        }
    }
//    fun postPhieuKham(IdTK: String,IdBS: String,idKH:String,NgayKham:String,GioKham:String,tinhtrang:String,Note:String)=
//        liveData(Dispatchers.IO) {
//            emit(DataResult.loading())
//            try {
//                emit(DataResult.success(repository.postPhieuKham(IdTK, IdBS , idKH, NgayKham , GioKham, tinhtrang, Note)))
//            }
//            catch (e:Exception){
//                Log.e("Okhttp", e.toString())
//                emit(DataResult.error(e.message?:"Error Data"))
//
//            }
//        }
}