package org.example.back_end.controller;

import org.apache.tomcat.util.http.parser.Authorization;
import org.example.back_end.dto.*;
import org.example.back_end.dto.formDTO.AppointmentDetailsDTO;
import org.example.back_end.entity.Pet;
import org.example.back_end.service.*;
import org.example.back_end.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private EmailService emailService;


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
        emailService.sendAppointmentEmail(userDTO.getName(), userDTO.getEmail(), "Appointment Confirmation",appointmentDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Appointment Added success", null));
    }

    @GetMapping("/getAllAppointmentsFromUser")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ResponseDTO> getAppointmentsFromUser(@RequestHeader("Authorization") String Authorization) {
        //Extract the user’s email from the token.
        String email = userService.getUserByToken(Authorization.substring(7)).getEmail();

        //Retrieve the user.
        UserDTO userDTO = userService.searchUser(email);

        //Retrieve all appointments from the user.
        List<AppointmentDTO> appointmentDTOList = appointmentService.getAllAppointmentsFromUser(userDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Appointments retrieved successfully", appointmentDTOList));
    }

    @GetMapping("/getAllAppointments")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllAppointments(@RequestHeader("Authorization") String Authorization) {
        List<AppointmentDTO> appointmentDTOList = appointmentService.getAllAppointments();
        if (appointmentDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "No appointments found", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Appointments retrieved successfully", appointmentDTOList));
    }

    @DeleteMapping("/deleteAppointment")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ResponseDTO> deleteAppointment(@RequestParam int id) {
        boolean isDeleted = appointmentService.deleteAppointment(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Appointment deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "Appointment not found", null));
        }
    }

}
