package org.example.back_end.controller;

import org.example.back_end.dto.PetDTO;
import org.example.back_end.dto.ResponseDTO;
import org.example.back_end.dto.VeterinarianDTO;
import org.example.back_end.entity.MedicalReport;
import org.example.back_end.service.MedicalReportService;
import org.example.back_end.service.PetService;
import org.example.back_end.service.VeterinarianService;
import org.example.back_end.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/MedicalReport")
@CrossOrigin
public class MedicalReportController {

    private final MedicalReportService medicalReportService;
    private final VeterinarianService veterinarianService;
    private final PetService petService;

    public MedicalReportController(MedicalReportService medicalReportService, VeterinarianService veterinarianService, PetService petService) {
        this.medicalReportService = medicalReportService;
        this.veterinarianService = veterinarianService;
        this.petService = petService;
    }

    //save medical report
    @PostMapping("/save")
//    @PreAuthorize("hasAnyAuthority('ADMIN','VETERINARIAN')")
    public ResponseEntity<ResponseDTO> saveMedicalReport(@RequestBody MedicalReport medicalReport) {
        //Retrieve the pet
        PetDTO petDTO = petService.searchPet(medicalReport.getPet().getPetId());
        System.out.println("Pet found: " + petDTO);

        //Retrieve the veterinarian
        VeterinarianDTO veterinarianDTO = veterinarianService.searchVeterinarian(medicalReport.getVeterinarian().getId());
        System.out.println("Veterinarian found: " + veterinarianDTO);
        //save the medical report
        medicalReportService.saveMedicalReport(medicalReport, petDTO, veterinarianDTO);
        System.out.println("Medical Report saved: " + medicalReport);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Medical Report Added success", medicalReport));


    }


}
