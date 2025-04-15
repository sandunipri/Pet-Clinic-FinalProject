package org.example.back_end.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.back_end.entity.Pet;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private UUID uid;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must be at most 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$",
            message = "Email must be a valid Gmail address")
    private String email;

    @Size(min = 8, max = 100, message = "Password must be 8-100 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase, one lowercase, one number and one special character"
    )
    private String password;

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name should contain only letters and spaces")
    @Size(min = 2, max = 50, message = "Name must be 2-50 characters")
    private String name;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(USER|ADMIN|VET)$", message = "Role must be USER, ADMIN or VET")
    private String role;

    @Size(max = 200, message = "Address must be at most 200 characters")
    private String address;

    @NotBlank(message = "Telephone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Telephone number must be 10 digits")
    private String telNo;

    @Pattern(regexp = "^(MALE|FEMALE|OTHER)?$", message = "Gender must be MALE, FEMALE or OTHER")
    private String gender;

    @Pattern(
            regexp = "^([0-9]{9}[xXvV]|[0-9]{12})?$",
            message = "NIC must be either 9 digits with X/V or 12 digits"
    )
    private String nic;

    @Pattern(regexp = "^[0-9]{10}$", message = "Emergency contact must be 10 digits")
    private String emergencyContact;

    @Size(max = 50, message = "Emergency contact name must be at most 50 characters")
    private String emergencyContactName;

    @Size(max = 255, message = "Profile image URL must be at most 255 characters")
    private String profileImage;

    private boolean isGoogleUser;;
}
