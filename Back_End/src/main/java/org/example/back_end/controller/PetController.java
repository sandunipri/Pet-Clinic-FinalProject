package org.example.back_end.controller;

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
public class PetController {

    @Autowired
    private PetServiceImpl petService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) {
        System.out.println(petDTO);
        petService.savePet(petDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "pet Added success", petDTO));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllPets() {
        System.out.println("get all pets");

        List<PetDTO> petList = petService.getAllPets();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "success", petList));
    }

}
