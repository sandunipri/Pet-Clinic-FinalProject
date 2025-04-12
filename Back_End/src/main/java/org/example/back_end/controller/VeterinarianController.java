package org.example.back_end.controller;

import jakarta.servlet.annotation.MultipartConfig;
import org.example.back_end.dto.ResponseDTO;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.VeterinarianDTO;
import org.example.back_end.dto.formDTO.AddVeterinarianFormDTO;
import org.example.back_end.service.FileStorageService;
import org.example.back_end.service.UserService;
import org.example.back_end.service.VeterinarianService;
import org.example.back_end.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/veterinarian")
@CrossOrigin
@MultipartConfig(fileSizeThreshold = 10 * 1024 * 1024,
        maxFileSize = 10 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024)

public class VeterinarianController {
    private final VeterinarianService veterinarianService;
    private final FileStorageService fileStorageService;
    private final UserService userService;

    public VeterinarianController(VeterinarianService veterinarianService, FileStorageService fileStorageService, UserService userService) {
        this.veterinarianService = veterinarianService;
        this.fileStorageService = fileStorageService;
        this.userService = userService;
    }

    @PostMapping( value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> saveVeterinarian(@ModelAttribute AddVeterinarianFormDTO addVeterinarianFormDTO) {
        System.out.println(addVeterinarianFormDTO);

        //saved Image
        String savedPath = fileStorageService.saveVetProfileImage(addVeterinarianFormDTO.getProfileImage());

        //convert form data to VeterinarianDTO
        VeterinarianDTO veterinarianDTO = veterinarianService.convertFormToVeterinarianDTO(addVeterinarianFormDTO, savedPath);

        veterinarianService.saveVeterinarian(veterinarianDTO);
        return ResponseEntity.ok(new ResponseDTO(201, "Veterinarian is saved", veterinarianDTO));
    }

    @GetMapping("getAllVeterinary")
    public ResponseEntity<ResponseDTO> getAllVeterinary() {
        List<VeterinarianDTO> veterinarianDTOList = veterinarianService.getAllVeterinarian();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Veterinary List", veterinarianDTOList));
    }
    @GetMapping("/getAllVetsCount")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllVetsCount() {
        System.out.println("getAllVetsCount");
        int count = veterinarianService.getAllVetsCount();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK,"Veterinarian Count", count));
    }

    @GetMapping("/getVetProfile")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getVetProfile(@RequestParam int id) {
        VeterinarianDTO veterinarianDTO = veterinarianService.searchVeterinarian(id);
        if (veterinarianDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Veterinarian Profile", veterinarianDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "Veterinarian Not Found", null));
        }
    }

    @PutMapping(path = "/updateVeterinarian", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> updateVeterinarian(@RequestHeader ("Authorization") String Authorization,@ModelAttribute AddVeterinarianFormDTO addVeterinarianFormDTO) {

            VeterinarianDTO existVet = veterinarianService.searchVeterinarian(addVeterinarianFormDTO.getId());
            String imagePath = existVet.getProfileImage();

            if (addVeterinarianFormDTO.getProfileImage() != null && !addVeterinarianFormDTO.getProfileImage().isEmpty()) {
                imagePath = fileStorageService.saveVetProfileImage(addVeterinarianFormDTO.getProfileImage());
            }

            UserDTO userDTO = userService.getUserByToken(Authorization.substring(7));
            System.out.println(userDTO);


            VeterinarianDTO veterinarianDTO = veterinarianService.updateVeterinarianDetails(addVeterinarianFormDTO);
            veterinarianDTO.setProfileImage(imagePath);

            veterinarianService.updateVeterinarian(veterinarianDTO);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Veterinarian Updated", veterinarianDTO));

    }

    @DeleteMapping("/deleteVeterinarian")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteVeterinarian(@RequestParam int id) {
        boolean isDeleted = veterinarianService.deleteVeterinarian(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "Veterinarian Not Found", null));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Veterinarian Deleted", isDeleted));

        }
    }
}
