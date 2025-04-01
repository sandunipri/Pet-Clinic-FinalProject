package org.example.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VeterinarianDTO {
    private int id;
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
    private String profileImage;
}
