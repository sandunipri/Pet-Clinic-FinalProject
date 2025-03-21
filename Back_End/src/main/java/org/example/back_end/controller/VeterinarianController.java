package org.example.back_end.controller;

import org.example.back_end.dto.ResponseDTO;
import org.example.back_end.dto.VeterinarianDTO;
import org.example.back_end.service.impl.VeterinarianServiceImpl;
import org.example.back_end.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/veterinarian")
@CrossOrigin
public class VeterinarianController {

    @Autowired
    private VeterinarianServiceImpl veterinarianService;

    @PostMapping( "/save")
    public ResponseEntity<ResponseDTO> saveVeterinarian(@RequestBody VeterinarianDTO veterinarian) {
        System.out.println(veterinarian);

        veterinarianService.saveVeterinarian(veterinarian);
        return ResponseEntity.ok(new ResponseDTO(201, "Veterinarian is saved", veterinarian));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllVeterinarian() {
        System.out.println("getAllVeterinarian");

        List<VeterinarianDTO> veterinarianList = veterinarianService.getAllVeterinarian();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK,"Veterinarian List", veterinarianList));
    }

}
