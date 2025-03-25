package org.example.back_end.service.impl;

import org.example.back_end.dto.AppointmentDTO;
import org.example.back_end.dto.PetDTO;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.VeterinarianDTO;
import org.example.back_end.entity.Appointments;
import org.example.back_end.entity.Pet;
import org.example.back_end.entity.User;
import org.example.back_end.entity.Veterinarian;
import org.example.back_end.repo.AppointmentRepo;
import org.example.back_end.repo.PetRepo;
import org.example.back_end.repo.VeterinarianRepo;
import org.example.back_end.service.AppointmentService;
import org.example.back_end.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private VeterinarianRepo veterinarianRepo;

    @Autowired
    private PetRepo petRepo;


    @Override
    public void saveAppointment(AppointmentDTO appointmentDTO, UserDTO userDTO, PetDTO petDTO, VeterinarianDTO veterinarianDTO) {
        Appointments appointments = modelMapper.map(appointmentDTO, Appointments.class);
        User user = modelMapper.map(userDTO, User.class);
        Pet pet = modelMapper.map(petDTO, Pet.class);
        Veterinarian veterinarian = modelMapper.map(veterinarianDTO, Veterinarian.class);

        appointments.setUser(user);
        appointments.setPet(pet);
        appointments.setVeterinarian(veterinarian);

        appointmentRepo.save(appointments);
    }
}
