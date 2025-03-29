package org.example.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PetDTO {
    private int petId;
    private String petName;
    private String birthDate;
    private String species;
    private String weight;
    private String breed;
    private String age ;
    private String gender;
    private String petImage;
    private UserDTO user;
}

