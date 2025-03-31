package org.example.back_end.dto.formDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.back_end.entity.User;
import org.springframework.web.multipart.MultipartFile;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AddPetFormDTO {
    private int petId;
    private MultipartFile petImage;
    private String petName;
    private Date birthDate;
    private String species;
    private double weight;
    private String breed;
    private int age ;
    private String gender;

}
