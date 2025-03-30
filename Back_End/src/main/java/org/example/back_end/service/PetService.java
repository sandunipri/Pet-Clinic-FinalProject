package org.example.back_end.service;

import org.example.back_end.dto.PetDTO;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.formDTO.AddPetFormDTO;

import java.util.List;

public interface PetService {
    void savePet(PetDTO petDTO);
    List<PetDTO> getAllPets();
    PetDTO searchPet(int petId);
    PetDTO searchPetByUserEmail(String email);
    PetDTO convertFormToPetDTO(AddPetFormDTO addPetFormDTO);
    List<PetDTO> getPetsFromUser(UserDTO userDTO);
}
