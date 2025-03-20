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
    private String Species;
    private String Breed;
    private String Age ;
    private String Gender;
}
