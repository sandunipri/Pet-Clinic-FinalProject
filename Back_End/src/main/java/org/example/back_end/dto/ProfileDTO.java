package org.example.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProfileDTO {
    private String email;
    private String name;
    private String phone;
}
