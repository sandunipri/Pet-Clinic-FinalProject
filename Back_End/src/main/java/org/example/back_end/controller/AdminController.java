package org.example.back_end.controller;

import jakarta.servlet.annotation.MultipartConfig;
import org.example.back_end.dto.*;
import org.example.back_end.dto.formDTO.RegisterFormDTO;
import org.example.back_end.dto.tableModalDTO.UserTableModalDTO;
import org.example.back_end.service.FileStorageService;
import org.example.back_end.service.PetService;
import org.example.back_end.service.UserService;
import org.example.back_end.service.VeterinarianService;
import org.example.back_end.util.JwtUtil;
import org.example.back_end.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
@CrossOrigin
@MultipartConfig(fileSizeThreshold = 10 * 1024 * 1024,
        maxFileSize = 10 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024)

public class AdminController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final FileStorageService fileStorageService;
    private final VeterinarianService veterinarianService;
    private final PetService petService;

    public AdminController(UserService userService, JwtUtil jwtUtil, FileStorageService fileStorageService, VeterinarianService veterinarianService, PetService petService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.fileStorageService = fileStorageService;
        this.veterinarianService = veterinarianService;
        this.petService = petService;
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> registerUser(@ModelAttribute RegisterFormDTO registerFormDTO) {
        UserDTO userDTO = userService.convertFormToUserDTO(registerFormDTO, null);

        try {
            int res = userService.saveAdmin(userDTO);
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

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllUsers(@RequestHeader("Authorization") String Authorization) {
        System.out.println(Authorization);

        //Extract the user’s email from the token.
        UserDTO userDTO = userService.getUserByToken(Authorization.substring(7));

        List<UserTableModalDTO> userTableModalDTOS = userService.getAllUsers(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "User List", userTableModalDTOS));
    }

    @GetMapping("getAllVeterinary")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllVeterinary() {
        List<VeterinarianDTO> veterinarianDTOList = veterinarianService.getAllVeterinarian();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Veterinary List", veterinarianDTOList));
    }

    @GetMapping("/getAllPets")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllPets() {
        List<PetDTO> userDTOList = petService.getAllPets();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Pet List", userDTOList));
    }

}
