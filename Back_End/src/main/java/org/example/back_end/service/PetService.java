package org.example.back_end.service;

import org.example.back_end.dto.PetDTO;
import org.example.back_end.dto.UserDTO;

import java.util.List;

public interface PetService {
    void savePet(PetDTO petDTO, UserDTO userDTO);

    List<PetDTO> getAllPets();

    PetDTO searchPet(int petId);
}
