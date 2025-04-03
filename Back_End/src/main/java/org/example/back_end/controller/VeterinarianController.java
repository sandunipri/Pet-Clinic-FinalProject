package org.example.back_end.controller;

import jakarta.servlet.annotation.MultipartConfig;
import org.example.back_end.dto.ResponseDTO;
import org.example.back_end.dto.VeterinarianDTO;
import org.example.back_end.dto.formDTO.AddVeterinarianFormDTO;
import org.example.back_end.service.FileStorageService;
import org.example.back_end.service.VeterinarianService;
import org.example.back_end.service.impl.VeterinarianServiceImpl;
import org.example.back_end.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    public VeterinarianController(VeterinarianService veterinarianService, FileStorageService fileStorageService) {
        this.veterinarianService = veterinarianService;
        this.fileStorageService = fileStorageService;
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

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllVeterinarian() {
        System.out.println("getAllVeterinarian");
        List<VeterinarianDTO> veterinarianList = veterinarianService.getAllVeterinarian();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK,"Veterinarian List", veterinarianList));
    }

}
