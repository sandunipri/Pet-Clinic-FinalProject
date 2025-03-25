package org.example.back_end.service.impl;

import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.VeterinarianDTO;
import org.example.back_end.dto.formDTO.AddVeterinarianFormDTO;
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
        VeterinarianDTO veterinarianDTO = new VeterinarianDTO();
        veterinarianDTO.setName(addVeterinarianFormDTO.getName());
        veterinarianDTO.setEmail(addVeterinarianFormDTO.getEmail());
        veterinarianDTO.setAddress(addVeterinarianFormDTO.getAddress());
        veterinarianDTO.setPhone(addVeterinarianFormDTO.getPhone());
        veterinarianDTO.setSpecialty(addVeterinarianFormDTO.getSpecialty());
        veterinarianDTO.setProfileImage(savedPath);

        return veterinarianDTO;
    }

    @Override
    public VeterinarianDTO searchVeterinarian(int id) {
        return veterinarianRepo.findById(id)
                .map(veterinarian -> modelMapper.map(veterinarian, VeterinarianDTO.class))
                .orElse(null);

    }
}
