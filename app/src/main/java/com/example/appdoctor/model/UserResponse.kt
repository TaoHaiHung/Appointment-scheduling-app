package com.example.appdoctor.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//"IdTK": "2",
//"username": "hung",
//"email": "taohung2002@gmail.com",
//"pass": "123"

data class UserModel( var response: UserResponse)
data class UserResponse (
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") var listUser:ArrayList<User>?=null)
@Parcelize
data class User(
    @SerializedName("IdTK") val IdTK: String? = null,
    @SerializedName("username") val username: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("pass") val pass: String? = null,
) :Parcelable