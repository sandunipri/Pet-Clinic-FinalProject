package org.example.back_end.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.back_end.dto.AppointmentDTO;
import org.example.back_end.dto.PetDTO;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.VeterinarianDTO;
import org.example.back_end.dto.formDTO.AppointmentDetailsDTO;
import org.example.back_end.entity.Appointments;
import org.example.back_end.entity.Pet;
import org.example.back_end.entity.User;
import org.example.back_end.entity.Veterinarian;
import org.example.back_end.repo.AppointmentRepo;
import org.example.back_end.repo.PetRepo;
import org.example.back_end.repo.UserRepository;
import org.example.back_end.repo.VeterinarianRepo;
import org.example.back_end.service.AppointmentService;
import org.example.back_end.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private VeterinarianRepo veterinarianRepo;

    @Autowired
    private PetRepo petRepo;


    @Override
    public void saveAppointment(AppointmentDTO appointmentDTO) {
        Appointments appointments = modelMapper.map(appointmentDTO, Appointments.class);
        appointmentRepo.save(appointments);
    }

    @Override
    public AppointmentDTO setDetails(AppointmentDetailsDTO appointmentDetails, UserDTO userDTO, PetDTO petDTO, VeterinarianDTO veterinarianDTO) {
        AppointmentDTO appointmentDTO = new AppointmentDTO();

        appointmentDTO.setDate(appointmentDetails.getDate());
        appointmentDTO.setTime(appointmentDetails.getTime());
        appointmentDTO.setReason(appointmentDetails.getReason());

        appointmentDTO.setUser(userDTO);
        appointmentDTO.setPet(petDTO);
        appointmentDTO.setVeterinarian(veterinarianDTO);

        return appointmentDTO;
    }

    @Override
    public List<AppointmentDTO> getAllAppointmentsFromUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        String jpql = "SELECT a FROM Appointments a WHERE a.user = :user";

        TypedQuery<Appointments> query = entityManager.createQuery(jpql, Appointments.class);
        query.setParameter("user", user);

        List<Appointments> appointments = query.getResultList();
        return modelMapper.map(appointments, new TypeToken<List<AppointmentDTO>>() {
        }.getType());

    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return modelMapper.map(appointmentRepo.findAll(), new TypeToken<List<AppointmentDTO>>(){}.getType());
    }

    @Override
    public boolean deleteAppointment(int appointmentId) {
        if (appointmentRepo.existsById(appointmentId)){
            appointmentRepo.deleteById(appointmentId);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public AppointmentDTO updateAppointmentDetails(AppointmentDetailsDTO appointmentDetails) {

        Appointments appointments = appointmentRepo.findById(appointmentDetails.getId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointments.setDate(appointmentDetails.getDate());
        appointments.setTime(appointmentDetails.getTime());
        appointments.setReason(appointmentDetails.getReason());

        return modelMapper.map(appointments, AppointmentDTO.class);

    }

    @Override
    public void updateAppointment(AppointmentDTO appointmentDTO) {
        if (appointmentRepo.existsById(appointmentDTO.getId())){
            appointmentRepo.save(modelMapper.map(appointmentDTO, Appointments.class));
        }else {
            throw new RuntimeException("Appointment not found");
        }
    }
}
