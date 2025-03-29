package org.example.back_end.dto.formDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.back_end.entity.User;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AddPetFormDTO {
    private MultipartFile petImage;
    private String petName;
    private String birthDate;
    private String species;
    private String weight;
    private String breed;
    private String age ;
    private String gender;

}
