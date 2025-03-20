package org.example.back_end.controller;

import org.example.back_end.dto.ResponseDTO;
import org.example.back_end.dto.VeterinarianDTO;
import org.example.back_end.service.impl.VeterinarianServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/veterinarian")
@CrossOrigin
public class VeterinarianController {

    @Autowired
    private VeterinarianServiceImpl veterinarianService;

    @PostMapping(value = "/save")
    public ResponseEntity<ResponseDTO> saveVeterinarian(@RequestBody VeterinarianDTO veterinarian) {
        System.out.println(veterinarian);

        veterinarianService.saveVeterinarian(veterinarian);
        return ResponseEntity.ok(new ResponseDTO(201, "Veterinarian is saved", null));
    }

}
