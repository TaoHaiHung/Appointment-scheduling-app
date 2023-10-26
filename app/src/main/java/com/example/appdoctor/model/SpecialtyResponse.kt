package com.example.appdoctor.model

import com.google.gson.annotations.SerializedName


data class SpecialtyModle( var response: SpecialtyResponse)
data class SpecialtyResponse(@SerializedName("data") var listSpecialty:ArrayList<Specialty>?=null)

data class Specialty(
    @SerializedName("IdChuyenKhoa") val idChuyenKhoa: String? = null,
    @SerializedName("Ten") val Ten: String? = null,
    @SerializedName("Image") val image: String? = null,

)
