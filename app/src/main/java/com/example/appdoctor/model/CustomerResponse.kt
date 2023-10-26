package com.example.appdoctor.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

//"idKH": "1",
//"IdTK": "1",
//"tenKH": "Tào Hải Hưng",
//"sdtKH": "0971992583",
//"ngaySinhKH": "2002-08-21",
//"gioiTinhKH": "Nam",
//"diaChiKH": "Hà Nội",
//"congviecKH": "Sinh Viên",
//"emailKH": "[value-9]"

data class CustomerModel(var response: CustomerResponse)

data class CustomerResponse(@SerializedName("data") var listCustomer: ArrayList<Customer>? = null)
@Parcelize
data class Customer(
    @SerializedName("idKH") val idKH: String? = null,
    @SerializedName("IdTK") val IdTK: String? = null,
    @SerializedName("tenKH") val tenKH: String? = null,
    @SerializedName("sdtKH") val sdtKH: String? = null,
    @SerializedName("ngaySinhKH") val ngaySinhKH: String? = null,
    @SerializedName("gioiTinhKH") var gioiTinhKH: String? = null,
    @SerializedName("diaChiKH") val diaChiKH: String? = null,
    @SerializedName("congviecKH") val congviecKH: String? = null,
    @SerializedName("emailKH") val emailKH: String? = null,
): Parcelable