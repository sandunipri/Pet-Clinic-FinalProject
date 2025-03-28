package org.example.back_end.dto.formDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RegisterFormDTO {
    private MultipartFile profileImage;
    private String email;
    private String name;
    private String password;
    private String address;
    private String telNo;
    private String gender;
    private String nic;
    private String emergencyContact;
    private String emergencyContactName;
}
