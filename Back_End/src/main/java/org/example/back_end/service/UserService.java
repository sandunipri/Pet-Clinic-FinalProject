package org.example.back_end.service;


import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.formDTO.RegisterFormDTO;

public interface UserService {
    int saveUser(UserDTO userDTO);
    int updateUser(UserDTO userDTO);
    UserDTO searchUser(String email);
    String getUserRoleByToken(String token);
    UserDTO convertFormToUserDTO(RegisterFormDTO registerFormDTO, String savedPath);
    UserDTO getUserByToken(String substring);

    boolean deleteUser(String token);
}