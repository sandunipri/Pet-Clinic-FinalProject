package org.example.back_end.dto;

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
    private String email;
    private String password;
    private String name;
    private String role;
    private String address;
    private String telNo;
    private String gender;
    private String nic;
    private String emergencyContact;
    private String emergencyContactName;
    private String profileImage;
    private boolean isGoogleUser;
}
