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
    private MultipartFile profileImage;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String specialty;

}
