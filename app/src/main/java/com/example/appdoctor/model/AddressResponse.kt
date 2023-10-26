package com.example.appdoctor.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


//{
//    "IdCS": "1",
//    "TenCS": "Chi nhánh 1",
//    "diaChi": "152/6 Thành Thái, Phường 12, Quận 10, TP.HCM",
//    "IdBV": null,
//    "IdPhongKham": "1"
//}

data class AddressModel( var response: AddressResponse)
data class AddressResponse(@SerializedName("data") var listAddress:ArrayList<Address>?=null)
@Parcelize
data class Address(
    @SerializedName("IdCS") val idCS: String? = null,
    @SerializedName("TenCS") val nameCS: String? = null,
    @SerializedName("diaChi") val address: String? = null,
    @SerializedName("IdBV") val idHospital: String? = null,
    @SerializedName("IdPhongKham") val idClinic: String? = null,
): Parcelable
