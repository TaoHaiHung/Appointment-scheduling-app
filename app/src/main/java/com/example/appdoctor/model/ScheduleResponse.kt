package com.example.appdoctor.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
//"idSchedule": "1",
//"IdBS": "1",
//"idPK": "1",
//"time": "15:00-16:00"

data class ScheduleModel( var response: ScheduleResponse)
data class ScheduleResponse (
    @SerializedName("data") var listSchedule:ArrayList<Schedule>?=null)
@Parcelize
data class Schedule(
    @SerializedName("idSchedule") val idSchedule: String? = null,
    @SerializedName("IdBS") val IdBS: String? = null,
    @SerializedName("idPK") val idPK: String? = null,
    @SerializedName("time") val time: String? = null,
) : Parcelable