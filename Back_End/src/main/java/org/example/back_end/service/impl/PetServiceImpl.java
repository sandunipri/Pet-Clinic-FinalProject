package org.example.back_end.service.impl;

import org.example.back_end.dto.PetDTO;
import org.example.back_end.entity.Pet;
import org.example.back_end.repo.PetRepo;
import org.example.back_end.service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepo petRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public void savePet(PetDTO petDTO) {

    }
}
