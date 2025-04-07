package org.example.back_end.controller;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;

import org.example.back_end.dto.AuthDTO;
import org.example.back_end.dto.ResponseDTO;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.formDTO.RegisterFormDTO;
import org.example.back_end.service.FileStorageService;
import org.example.back_end.service.UserService;
import org.example.back_end.util.JwtUtil;
import org.example.back_end.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
@MultipartConfig(fileSizeThreshold = 10 * 1024 * 1024,
        maxFileSize = 10 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024)

public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final FileStorageService fileStorageService;

    //constructor injection
    public UserController(UserService userService, JwtUtil jwtUtil, FileStorageService fileStorageService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.fileStorageService = fileStorageService;
    }


    @GetMapping( "/getProfile")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<ResponseDTO> getUser(@RequestHeader("Authorization") String authorization ){
        System.out.println(authorization);

        UserDTO userDTO = userService.getUserByToken(authorization.substring(7));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", userDTO));
    }

    @PutMapping(path = "/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<ResponseDTO> updateUser(@ModelAttribute RegisterFormDTO registerFormDTO) {
        UserDTO existUser = userService.searchUser(registerFormDTO.getEmail());

        if (registerFormDTO.getProfileImage() != null && !registerFormDTO.getProfileImage().isEmpty()) {
            String savedPath = fileStorageService.saveProfileImage(registerFormDTO.getProfileImage());
            existUser.setProfileImage(savedPath);
        }else {
            existUser.setProfileImage(existUser.getProfileImage());
        }

        existUser.setName(registerFormDTO.getName());
        existUser.setAddress(registerFormDTO.getAddress());
        existUser.setTelNo(registerFormDTO.getTelNo());
        existUser.setGender(registerFormDTO.getGender());
        existUser.setNic(registerFormDTO.getNic());
        existUser.setEmergencyContact(registerFormDTO.getEmergencyContact());
        existUser.setEmergencyContactName(registerFormDTO.getEmergencyContactName());

        int res = userService.updateUser(existUser);
        if (res == VarList.OK) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.OK, "Success", existUser));
        }
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));

    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<ResponseDTO> deleteUser(@RequestHeader("Authorization") String authorization ){
        boolean isDeleted = userService.deleteUser(authorization.substring(7));
        if (isDeleted){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Success", null));
        }

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(new ResponseDTO(VarList.Bad_Gateway, "Failed", null));
    }

    @GetMapping("/logAgain")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ResponseDTO> logAgain(@RequestHeader("Authorization") String authorization ){
        System.out.println("sssss");
        String token = authorization.substring(7);
        String role = userService.getUserRoleByToken(token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "success",role));
    }


/*
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            int res = userService.saveUser(userDTO);
            switch (res) {
                case VarList.Created -> {
                    String token = jwtUtil.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO();
                    authDTO.setUserDTO(userDTO);
                    authDTO.setToken(token);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
*/

}
