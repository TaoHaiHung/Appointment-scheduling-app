package com.example.appdoctor.db


import android.provider.ContactsContract.CommonDataKinds.Email
import retrofit2.Response
import javax.inject.Inject

class DataRemoteAPI @Inject constructor(private val apiServices: APIServices) {
    // List Doctor
    suspend fun getListBacSi() =apiServices.getListBacSi()

    // List Specialist
    suspend fun getListChuyenKhoa()=apiServices.getListChuyenKhoa()

    // List Hospital
    suspend fun getListBenhVien()=apiServices.getListBenhVien()

    // List Clinic
    suspend fun getListPhongKham() =apiServices.getListPhongKham()

    //Login
    suspend fun login(username:String,pass:String)=apiServices.login(username,pass)

    //Resister
    suspend fun register(username: String, email: String, pass: String)=apiServices.register(username,email,pass)

    // List CustomersById  (IdTK)
    suspend fun getCustomersByUserId(idTK:String)= apiServices.getCustomersByUserId(idTK)

    suspend fun postCustomers(IdTK: String,tenKH:String,sdtKH:String,ngaySinhKH:String,gioiTinhKH:String,congviecKH:String,diaChiKH:String,emailKH: String)
    = apiServices.postCustomers(IdTK, tenKH , sdtKH , ngaySinhKH , gioiTinhKH , congviecKH, diaChiKH,emailKH)

    // List ScheduleByDoctor (IdBS)
    suspend fun getScheduleByDoctor(IdBS:String)=apiServices.getScheduleByDoctor(IdBS)

    // List ScheduleByClinic (IdPhongKham)
    suspend fun getScheduleByClinic(idClinic: String)=apiServices.getScheduleByClinic(idClinic)

    // List addressByAddress
    suspend fun getAddressByClinic(idClinic:String)=apiServices.getAddressByClinic(idClinic)

    // List PhieuKham(IdTK)
    suspend fun getPhieuKhamByIdTK(IdTK:String, tenKH: String)=apiServices.getPhieuKhamByIdTK(IdTK,tenKH)

    // Dat Kham
    suspend fun postPhieuKham(IdTK: String,IdBS: String,idKH:String,NgayKham:String,GioKham:String,tinhtrang:String,Note:String)
            = apiServices.postPhieuKham(IdTK, IdBS , idKH, NgayKham , GioKham, tinhtrang, Note)

    // Dat Kham
    suspend fun postPhieuKhamByClinic(IdTK: String,IdPhongKham: String,idKH:String,diaChi:String,NgayKham:String,GioKham:String,tinhtrang:String,Note:String)
            = apiServices.postPhieuKhamByClinic(IdTK, IdPhongKham , idKH, diaChi,NgayKham , GioKham, tinhtrang, Note)
    // Huy PhieuKham
    suspend fun deletePhieuKham(idPK:String, tinhtrang: String)=apiServices.deletePhieuKham(idPK,tinhtrang)
}