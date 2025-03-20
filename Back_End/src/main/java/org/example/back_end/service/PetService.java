package org.example.back_end.service;

import org.example.back_end.dto.PetDTO;

import java.util.List;

public interface PetService {
    void savePet(PetDTO petDTO);

    List<PetDTO> getAllPets();
}
