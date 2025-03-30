package org.example.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PetDTO {
    private int petId;
    private String petName;
    private Date birthDate;
    private String species;
    private double weight;
    private String breed;
    private int age ;
    private String gender;
    private String petImage;
    private UserDTO user;
}

