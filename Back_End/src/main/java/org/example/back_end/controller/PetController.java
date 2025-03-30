package org.example.back_end.controller;

import jakarta.servlet.annotation.MultipartConfig;
import org.apache.tomcat.util.http.parser.Authorization;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.formDTO.AddPetFormDTO;
import org.example.back_end.dto.formDTO.RegisterFormDTO;
import org.example.back_end.service.FileStorageService;
import org.example.back_end.service.PetService;
import org.example.back_end.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.example.back_end.dto.PetDTO;
import org.example.back_end.dto.ResponseDTO;
import org.example.back_end.service.impl.PetServiceImpl;
import org.example.back_end.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("api/v1/pet")
@CrossOrigin
@MultipartConfig(fileSizeThreshold = 10 * 1024 * 1024,
        maxFileSize = 10 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024)

public class PetController {
    private final PetService petService;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    public PetController(PetService petService, UserService userService, FileStorageService fileStorageService) {
        this.petService = petService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

/*    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) {
        System.out.println(petDTO);
        petService.savePet(petDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "pet Added success", petDTO));
    }*/

    @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ResponseDTO> addPet(@RequestHeader("Authorization") String Authorization, @ModelAttribute AddPetFormDTO addPetFormDTO) {
        //Extract the user’s email from the token.
        UserDTO userDTO = userService.getUserByToken(Authorization.substring(7));
        System.out.println("userDTO" + userDTO);

        //save the imagePath
        String imagePath = fileStorageService.savePetImage(addPetFormDTO.getPetImage());
        System.out.println("imagePath" + imagePath);

        //convert the form to petDTO
        PetDTO petDTO = petService.convertFormToPetDTO(addPetFormDTO);
        petDTO.setUser(userDTO);
        petDTO.setPetImage(imagePath);

        petService.savePet(petDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "pet Added success", petDTO));
    }


/*    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ResponseDTO> getAllPets() {
        System.out.println("get all pets");

        List<PetDTO> petList = petService.getAllPets();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "success", petList));
    }*/

    @GetMapping("/getPetsFromUser")
    public ResponseEntity<ResponseDTO> getPetsFromUser(@RequestHeader("Authorization") String Authorization) {
        //get user email from the token.
        UserDTO userDTO = userService.getUserByToken(Authorization.substring(7));
        System.out.println("userDTO" + userDTO);

        //get the pets from the user
        List<PetDTO> petList = petService.getPetsFromUser(userDTO);
        System.out.println(petList);
        if (petList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "No pets found", petList));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "success", petList));
    }

}
