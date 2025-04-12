package org.example.back_end.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.VeterinarianDTO;
import org.example.back_end.dto.formDTO.AddVeterinarianFormDTO;
import org.example.back_end.entity.User;
import org.example.back_end.entity.Veterinarian;
import org.example.back_end.repo.VeterinarianRepo;
import org.example.back_end.service.VeterinarianService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarianServiceImpl implements VeterinarianService {

    @Autowired
    private VeterinarianRepo veterinarianRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager entityManager;



    @Override
    public void saveVeterinarian(VeterinarianDTO veterinarian) {
        veterinarianRepo.save(modelMapper.map(veterinarian, Veterinarian.class));
    }

    @Override
    public List<VeterinarianDTO> getAllVeterinarian() {
        return modelMapper.map(veterinarianRepo.findAll(),new TypeToken<List<VeterinarianDTO>>(){}.getType());
    }

    @Override
    public VeterinarianDTO convertFormToVeterinarianDTO(AddVeterinarianFormDTO addVeterinarianFormDTO, String savedPath) {
        VeterinarianDTO veterinarianDTO = modelMapper.map(addVeterinarianFormDTO, VeterinarianDTO.class);
        veterinarianDTO.setProfileImage(savedPath);
        return veterinarianDTO;
    }

    @Override
    public VeterinarianDTO searchVeterinarian(int id) {
        return veterinarianRepo.findById(id)
                .map(veterinarian -> modelMapper.map(veterinarian, VeterinarianDTO.class))
                .orElse(null);

    }

    @Override
    public int getAllVetsCount() {
        String query = "SELECT COUNT(v) FROM Veterinarian v";
        TypedQuery <Long> typedQuery = entityManager.createQuery(query, Long.class);
        Long count = typedQuery.getSingleResult();
        return count.intValue();
    }

    @Override
    public VeterinarianDTO updateVeterinarianDetails(AddVeterinarianFormDTO addVeterinarianFormDTO) {

        Veterinarian veterinarian = veterinarianRepo.getReferenceById(addVeterinarianFormDTO.getId());

        veterinarian.setTitle(addVeterinarianFormDTO.getTitle());
        veterinarian.setFirstName(addVeterinarianFormDTO.getFirstName());
        veterinarian.setLastName(addVeterinarianFormDTO.getLastName());
        veterinarian.setAddress(addVeterinarianFormDTO.getAddress());
        veterinarian.setPhone(addVeterinarianFormDTO.getPhone());
        veterinarian.setEmail(addVeterinarianFormDTO.getEmail());
        veterinarian.setLicenseNo(addVeterinarianFormDTO.getLicenseNo());
        veterinarian.setSpecialty(addVeterinarianFormDTO.getSpecialty());
        veterinarian.setYOEeperience(addVeterinarianFormDTO.getYOEeperience());

        return modelMapper.map(veterinarian, VeterinarianDTO.class);
    }

    @Override
    public void updateVeterinarian(VeterinarianDTO veterinarianDTO) {
        if (veterinarianRepo.existsById(veterinarianDTO.getId())) {
            veterinarianRepo.save(modelMapper.map(veterinarianDTO, Veterinarian.class));
        }else {
            throw new RuntimeException("Veterinarian not found");
        }
    }

}
