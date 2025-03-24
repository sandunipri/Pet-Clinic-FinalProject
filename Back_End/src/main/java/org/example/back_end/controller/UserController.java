package org.example.back_end.controller;

import jakarta.validation.Valid;

import org.example.back_end.dto.AuthDTO;
import org.example.back_end.dto.ResponseDTO;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.service.UserService;
import org.example.back_end.util.JwtUtil;
import org.example.back_end.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    //constructor injection
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping( "/getProfile")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<ResponseDTO> getUser(@RequestHeader("Authorization") String authorization ){
        System.out.println(authorization);

        UserDTO userDTO = userService.getUserByToken(authorization.substring(7));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", userDTO));
    }

    @PutMapping("/updateProfile")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UserDTO userDTO) {
        UserDTO existUser = userService.searchUser(userDTO.getEmail());
        existUser.setName(userDTO.getName());
        existUser.setAddress(userDTO.getAddress());
        existUser.setTelNo(userDTO.getTelNo());

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

}
