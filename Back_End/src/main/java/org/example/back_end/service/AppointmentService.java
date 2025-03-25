package org.example.back_end.service;

import org.example.back_end.dto.AppointmentDTO;
import org.example.back_end.dto.PetDTO;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.VeterinarianDTO;

public interface AppointmentService {


    void saveAppointment(AppointmentDTO appointmentDTO, UserDTO userDTO, PetDTO petDTO, VeterinarianDTO veterinarianDTO);

}
