package org.example.back_end.service;


import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.formDTO.RegisterFormDTO;
import org.example.back_end.dto.tableModalDTO.UserTableModalDTO;

import java.util.List;

public interface UserService {
    int saveUser(UserDTO userDTO);
    int saveAdmin(UserDTO userDTO);
    int updateUser(UserDTO userDTO);
    UserDTO searchUser(String email);
    String getUserRoleByToken(String token);
    UserDTO convertFormToUserDTO(RegisterFormDTO registerFormDTO, String savedPath);
    UserDTO getUserByToken(String substring);
    boolean deleteUser(String token);

    List<UserTableModalDTO> getAllUsers(UserDTO userDTO);

    int getAllUsersCount();

}