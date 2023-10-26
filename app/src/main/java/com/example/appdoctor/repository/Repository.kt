package com.example.appdoctor.repository


import com.example.appdoctor.db.DataRemoteAPI
import javax.inject.Inject

class Repository @Inject constructor(private val dataRemoteAPI: DataRemoteAPI) {

    suspend fun getListBacSi() =dataRemoteAPI.getListBacSi()

    suspend fun getListChuyenKhoa()=dataRemoteAPI.getListChuyenKhoa()

    suspend fun getListBenhVien()=dataRemoteAPI.getListBenhVien()

    // List Clinic
    suspend fun getListPhongKham() =dataRemoteAPI.getListPhongKham()

    suspend fun login(username:String, pass:String)=dataRemoteAPI.login(username,pass )

    suspend fun register(username:String,email:String, pass:String)=dataRemoteAPI.register(username,email,pass)

    suspend fun getCustomersByUserId(idTK:String)=dataRemoteAPI.getCustomersByUserId(idTK)

    suspend fun postCustomers(idTK: String,tenKH:String,sdtKH:String,ngaySinhKH:String,gioiTinhKH:String,congviecKH:String,diaChiKH:String,emailKH: String)
            = dataRemoteAPI.postCustomers(idTK, tenKH , sdtKH , ngaySinhKH , gioiTinhKH , congviecKH, diaChiKH,emailKH,)

    suspend fun getScheduleByDoctor(IdBS:String)=dataRemoteAPI.getScheduleByDoctor(IdBS)

    suspend fun getPhieuKhamByIdTK(IdTK:String,tenKH: String)=dataRemoteAPI.getPhieuKhamByIdTK(IdTK,tenKH)

    // Dat Kham
    suspend fun postPhieuKham(IdTK: String,IdBS: String,idKH:String,NgayKham:String,GioKham:String,tinhtrang:String,Note:String)
            = dataRemoteAPI.postPhieuKham(IdTK, IdBS , idKH, NgayKham , GioKham, tinhtrang, Note)
    // Huy PhieuKham
    suspend fun deletePhieuKham(idPK:String, tinhtrang: String)=dataRemoteAPI.deletePhieuKham(idPK,tinhtrang)

    // List addressByAddress
    suspend fun getAddressByClinic(idClinic:String)=dataRemoteAPI.getAddressByClinic(idClinic)

    // List ScheduleByClinic (IdPhongKham)
    suspend fun getScheduleByClinic(idClinic: String)=dataRemoteAPI.getScheduleByClinic(idClinic)

    suspend fun postPhieuKhamByClinic(IdTK: String,IdPhongKham: String,idKH:String,diaChi:String,NgayKham:String,GioKham:String,tinhtrang:String,Note:String)
            = dataRemoteAPI.postPhieuKhamByClinic(IdTK, IdPhongKham , idKH, diaChi,NgayKham , GioKham, tinhtrang, Note)
}