package net.datasa.Gakusei_Kanri.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long id;

    // 엔티티에는 없던 fullName
    private String fullName;

    // 민감 정보는 DTO에서 가공된 형태
    private String maskedPhone;

    // boolean → 사용자 친화적 상태 값
    private String status;

    private Integer birthYear;

    private String email;

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) {
            return phone;
        }
        return phone.substring(0, phone.length() - 4) + "****";
    }
}
