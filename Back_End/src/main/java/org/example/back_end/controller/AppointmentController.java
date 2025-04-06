package org.example.back_end.controller;

import org.apache.tomcat.util.http.parser.Authorization;
import org.example.back_end.dto.*;
import org.example.back_end.dto.formDTO.AppointmentDetailsDTO;
import org.example.back_end.entity.Pet;
import org.example.back_end.service.AppointmentService;
import org.example.back_end.service.PetService;
import org.example.back_end.service.UserService;
import org.example.back_end.service.VeterinarianService;
import org.example.back_end.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/appointment")
@CrossOrigin
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @Autowired
    private VeterinarianService veterinarianService;


    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ResponseDTO> saveAppointment(@RequestHeader("Authorization") String Authorization,  @RequestBody AppointmentDetailsDTO appointmentDetails) {
        //Extract the user’s email from the token.

        String email = userService.getUserByToken(Authorization.substring(7)).getEmail();

        //Retrieve the user.
        UserDTO userDTO = userService.searchUser(email);

        //Retrieve the veterinarian.
        VeterinarianDTO veterinarianDTO = veterinarianService.searchVeterinarian(Integer.parseInt(appointmentDetails.getVetId()));

        PetDTO petDTO = petService.searchPet(Integer.parseInt(appointmentDetails.getPetId()));

        AppointmentDTO appointmentDTO = appointmentService.setDetails(appointmentDetails, userDTO, petDTO, veterinarianDTO);
        //save the appointment.
        appointmentService.saveAppointment(appointmentDTO);

        //send an email to the user.
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Appointment Added success", null));
    }
}
