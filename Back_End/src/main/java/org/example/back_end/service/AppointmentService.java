package org.example.back_end.service;

import org.example.back_end.dto.AppointmentDTO;
import org.example.back_end.dto.PetDTO;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.VeterinarianDTO;
import org.example.back_end.dto.formDTO.AppointmentDetailsDTO;

import java.util.List;

public interface AppointmentService {


    void saveAppointment(AppointmentDTO appointmentDTO);

    AppointmentDTO setDetails(AppointmentDetailsDTO appointmentDetails, UserDTO userDTO, PetDTO petDTO, VeterinarianDTO veterinarianDTO);

    List<AppointmentDTO> getAllAppointmentsFromUser(UserDTO userDTO);
}
