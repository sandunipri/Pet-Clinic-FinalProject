package org.example.back_end.service;


import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.formDTO.RegisterFormDTO;

public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String username);
    String getUserRoleByToken(String token);
    Object getUsers();

    UserDTO convertFormToUserDTO(RegisterFormDTO registerFormDTO, String savedPath);
}