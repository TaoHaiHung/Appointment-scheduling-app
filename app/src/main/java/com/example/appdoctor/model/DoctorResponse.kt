package com.example.appdoctor.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class DoctorModel(var response: DoctorResponse)
data class DoctorResponse(@SerializedName("data") var listDoctor:ArrayList<Doctor>?=null)
@Parcelize
data class Doctor(
    @SerializedName("IdBS") val idBacSi: String? = null,
    @SerializedName("TenBS") val TenBacSi: String? = null,
    @SerializedName("IdBV") val idBenhVien: String? = null,
    @SerializedName("chuyenkhoa") val chuyenkhoa: String? = null,
    @SerializedName("kinhNghiem") val kinhnghiem: String? = null,
    @SerializedName("diaChi") val diaChi: String? = null,
    @SerializedName("gioiThieuBS") val gioithieu: String? = null,
    @SerializedName("imageBS") val image: String? = null,
    @SerializedName("benhvien") val hospital: Hospital? = null,

):Parcelable