package com.example.appdoctor.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PhieuKhamModel(var response: PhieuKhamResponse)
data class PhieuKhamResponse(@SerializedName("data") var listPhieuKham:ArrayList<PhieuKham>?=null)
@Parcelize
data class PhieuKham(
    @SerializedName("idPK") val idPK: String? = null,
    @SerializedName("IdTK") val IdTK: String? = null,
    @SerializedName("IdBS") val IdBS: String? = null,
    @SerializedName("IdPhongKham") val idCLinic: String? = null,
    @SerializedName("idKH") val idKH: String? = null,
    @SerializedName("diaChiKham") val diaChi: String? = null,
    @SerializedName("NgayKham") val ngayKham: String? = null,
    @SerializedName("GioKham") val gioKham: String? = null,
    @SerializedName("tinhtrang") val tinhTrang: String? = null,
    @SerializedName("Note") val note: String? = null,
    @SerializedName("stt") val stt: String? = null,
    @SerializedName("doctor") val doctor: Doctor? = null, // Thêm thông tin của BacSi vào đây
    @SerializedName("phongkham") val clinic: Clinic? = null, // Thêm thông tin của BacSi vào đây
    @SerializedName("benhnhan") val benhnhan: Customer? = null // Thêm thông tin của BenhNhan vào đây
):Parcelable