package com.example.appdoctor.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

//"IdPK": "1",
//"TenPK": "Phòng khám Hello Doctor",
//"diaChi": "152/6 Thành Thái, Phường 12, Quận 10, TPHCM",
//"image": "https://taohaihung.000webhostapp.com/Image/img_HelloDoctor.png",
//"gioiThieu"
data class ClinicModel(var response: ClinicResponse)
data class ClinicResponse(@SerializedName("data") var listClinic:ArrayList<Clinic>?=null)
@Parcelize
data class Clinic(
    @SerializedName("IdPhongKham") val idPhongKham: String? = null,
    @SerializedName("TenPK") val TenPhongKham: String? = null,
    @SerializedName("diaChiChinh") val diaChi: String? = null,
    @SerializedName("gioiThieu") val gioithieu: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("chuyenSau") val chuyenSau: String? = null,
): Parcelable