package org.example.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VeterinarianDTO {
    private int id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String specialty;
    private String profileImage;
}
