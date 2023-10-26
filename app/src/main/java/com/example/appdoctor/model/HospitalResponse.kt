package com.example.appdoctor.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


//"IdBV": "1",
//"TenBV": "Bệnh Viện Bạch Mai",
//"diaChi": "78 Đường Giải Phóng, Phương Đông, Đống Đa, Hà Nội,...",
//"IdCS": "1",
//"gioiThieu": "Bệnh viện Bạch Mai, tọa lạc ở trung tâm Hà Nội, là một trong những bệnh viện lớn và uy tín nhất tại Việt Nam với hơn 100 năm lịch sử phục vụ cộng đồng. Với hai cơ sở cơ bản là Cơ sở 1 và Cơ sở 2, Bệnh viện Bạch Mai cung cấp dịch vụ y tế đa dạng, bao gồm khám và điều trị nội trú, ngoại trú, phẫu thuật, chẩn đoán hình ảnh, xét nghiệm, và chăm sóc sức khỏe toàn diện. Bệnh viện này nổi tiếng với đội ngũ chuyên gia hàng đầu trong nhiều lĩnh vực y học, từ nội tiết đến phụ sản và nhi khoa. Ngoài ra, Bệnh viện Bạch Mai còn đóng vai trò quan trọng trong việc đào tạo nhân lực y tế cho Việt Nam và tham gia vào nghiên cứu y học để cải thiện phương pháp điều trị và chẩn đoán. Đồng thời, bệnh viện cũng thường xuyên tham gia các hoạt động xã hội và từ thiện nhằm giúp đỡ những người cần thiết trong cộng đồng.",
//"image": "https://taohaihung.000webhostapp.com/Image/logo_bv_bach_mai.jpg"

data class HospitalModel(var response: HospitalResponse)
data class HospitalResponse(@SerializedName("data") var listHospital:ArrayList<Hospital>?=null)
@Parcelize
data class Hospital(
    @SerializedName("IdBV") val idBenhVien: String? = null,
    @SerializedName("TenBV") val TenBenhVien: String? = null,
    @SerializedName("diaChi") val diaChi: String? = null,
    @SerializedName("IdCS") val idCS: String? = null,
    @SerializedName("gioiThieu") val gioithieu: String? = null,
    @SerializedName("image") val image: String? = null,
): Parcelable