package org.example.back_end.controller;

import org.example.back_end.dto.AppointmentDTO;
import org.example.back_end.dto.ResponseDTO;
import org.example.back_end.service.AppointmentService;
import org.example.back_end.service.impl.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/appointment")
@CrossOrigin
public class AppointmentController {

    @Autowired
    private AppointmentServiceImpl appointmentService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        System.out.println("saveAppointment");

        appointmentService.saveAppointment(appointmentDTO);
        return ResponseEntity.ok(new ResponseDTO(201, "Appointment saved", appointmentDTO));
    }
}
