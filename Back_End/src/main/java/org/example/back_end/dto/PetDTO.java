package org.example.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {
    private int petId;

    @NotBlank(message = "Pet name is required")
    @Size(min = 2, max = 50, message = "Pet name must be between 2 and 50 characters")
    private String petName;

    @NotBlank(message = "Species is required")
    private String species;

    @Positive(message = "Weight must be a positive number")
    @DecimalMax(value = "200.0", message = "Weight cannot exceed 200 kg")
    private double weight;

    @Size(max = 50, message = "Breed cannot exceed 50 characters")
    private String breed;

    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 50, message = "Age cannot exceed 50 years")
    private int age;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female or Other")
    private String gender;

    private String petImage;

    @NotNull(message = "User information is required")
    private UserDTO user;
}

