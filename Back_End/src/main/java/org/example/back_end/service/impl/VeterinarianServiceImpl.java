package org.example.back_end.service.impl;

import org.example.back_end.dto.VeterinarianDTO;
import org.example.back_end.entity.Veterinarian;
import org.example.back_end.repo.VeterinarianRepo;
import org.example.back_end.service.VeterinarianService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
