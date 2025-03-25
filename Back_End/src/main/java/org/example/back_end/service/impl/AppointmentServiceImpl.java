package org.example.back_end.service.impl;

import org.example.back_end.dto.AppointmentDTO;
import org.example.back_end.entity.Appointments;
import org.example.back_end.entity.Pet;
import org.example.back_end.entity.Veterinarian;
import org.example.back_end.repo.AppointmentRepo;
import org.example.back_end.repo.PetRepo;
import org.example.back_end.repo.VeterinarianRepo;
import org.example.back_end.service.AppointmentService;
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
    private VeterinarianRepo veterinarianRepo;

    @Autowired
    private PetRepo petRepo;

    @Override
    public void saveAppointment(AppointmentDTO appointmentDTO) {
        Pet pet = petRepo.getReferenceById(appointmentDTO.getPet().getPetId());
        Veterinarian veterinarian = veterinarianRepo.getReferenceById(appointmentDTO.getVeterinarian().getId());

        Appointments appointments = modelMapper.map(appointmentDTO, Appointments.class);

        System.out.println("appointments: " + appointments);

        appointments.setPet(pet);
        appointments.setVeterinarian(veterinarian);
        appointmentRepo.save(appointments);
    }
}
