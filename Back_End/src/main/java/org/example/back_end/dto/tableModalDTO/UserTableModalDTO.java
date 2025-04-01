package org.example.back_end.dto.tableModalDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTableModalDTO {
    private String email;
    private String name;
    private String address;
    private String telNo;
    private String profileImage;
    private int petCount;
}
