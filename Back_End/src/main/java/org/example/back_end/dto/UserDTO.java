package org.example.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String profileImage;
}
