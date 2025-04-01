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
public class AddVeterinarianFormDTO {
    private int id;
    private MultipartFile profileImage;
    private String title;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String licenseNo;
    private String specialty;
    private String YOEeperience;
    private String bio;


}
