package com.example.appdoctor.db
import com.example.appdoctor.model.AddressResponse
import com.example.appdoctor.model.ClinicResponse
import com.example.appdoctor.model.CustomerResponse
import com.example.appdoctor.model.DoctorResponse
import com.example.appdoctor.model.HospitalResponse
import com.example.appdoctor.model.PhieuKhamResponse
import com.example.appdoctor.model.ScheduleResponse
import com.example.appdoctor.model.SpecialtyResponse
import com.example.appdoctor.model.UserResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
interface APIServices {

    @GET("BacSi/read.php")
    suspend fun getListBacSi():Response<DoctorResponse>

    @GET("ChuyenKhoa.php")
    suspend fun getListChuyenKhoa(): Response<SpecialtyResponse>

    @FormUrlEncoded
    @POST("demologin.php")
    suspend fun login(
        @Field("username") username: String,
        @Field("pass") password: String
    ): Response<UserResponse>

    @GET("BenhVien/read.php")
    suspend fun getListBenhVien() :Response<HospitalResponse>

    @FormUrlEncoded
    @POST("TaiKhoan/create.php")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("pass") pass:String
    ):Response<UserResponse>

    @GET("KhachHang/read.php")
    suspend fun getCustomersByUserId(
        @Query("IdTK") idTK: String
    ): Response<CustomerResponse>
@FormUrlEncoded
    @POST("KhachHang/create.php")
    suspend fun postCustomers(
        @Field("IdTK") IdTK: String,
        @Field("tenKH") tenKH: String,
        @Field("sdtKH") sdtKH: String,
        @Field("ngaySinhKH") ngaySinhKH: String,
        @Field("gioiTinhKH") gioiTinhKH: String,
        @Field("diaChiKH") diaChiKH: String,
        @Field("congviecKH") congviecKH: String,
        @Field("emailKH") emailKH: String,
    ):Response<CustomerResponse>

//    lịch khám
@GET("Schedule/read.php")
suspend fun getScheduleByDoctor(
    @Query("IdBS") IdBS: String
): Response<ScheduleResponse>

    @GET("Schedule/read.php")
    suspend fun getScheduleByClinic(
        @Query("IdPhongKham") idClinic: String
    ): Response<ScheduleResponse>

// Phieu Kham
@GET("PhieuKham/read.php")
suspend fun getPhieuKhamByIdTK(
    @Query("IdTK") IdTK: String,
    @Query("tenKH") tenKH:String
):Response<PhieuKhamResponse>

// Dat kham
@FormUrlEncoded
@POST("PhieuKham/create.php")
suspend fun postPhieuKham(
    @Field("IdTK") IdTK: String,
    @Field("IdBS") IdBS: String,
    @Field("idKH") idKH: String,
    @Field("NgayKham") NgayKham: String,
    @Field("GioKham") GioKham: String,
    @Field("tinhtrang") tinhtrang: String,
    @Field("Note") Note: String,
):Response<PhieuKhamResponse>

// hủy phiếu khám
@FormUrlEncoded
@POST("PhieuKham/update.php")
suspend fun deletePhieuKham(
    @Field("idPK") idPK :String,
    @Field("tinhtrang") tinhtrang: String,
):Response<PhieuKhamResponse>

    @GET("PhongKham/read.php")
    suspend fun getListPhongKham() :Response<ClinicResponse>

    // get address by clinic
    @GET("CoSo/read.php")
    suspend fun getAddressByClinic(
        @Query("IdPhongKham") idClinic: String
    ): Response<AddressResponse>

    @FormUrlEncoded
    @POST("PhieuKham/createByClinic.php")
    suspend fun postPhieuKhamByClinic(
        @Field("IdTK") IdTK: String,
        @Field("IdPhongKham") IdPhongKham: String,
        @Field("idKH") idKH: String,
        @Field("diaChi") diaChi: String,
        @Field("NgayKham") NgayKham: String,
        @Field("GioKham") GioKham: String,
        @Field("tinhtrang") tinhtrang: String,
        @Field("Note") Note: String,
    ):Response<PhieuKhamResponse>


}