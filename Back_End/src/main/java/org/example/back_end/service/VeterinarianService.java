package org.example.back_end.service;

import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.VeterinarianDTO;
import org.example.back_end.dto.formDTO.AddVeterinarianFormDTO;

import java.util.List;

public interface VeterinarianService {
    void saveVeterinarian(VeterinarianDTO veterinarian);
    List<VeterinarianDTO> getAllVeterinarian();
    VeterinarianDTO convertFormToVeterinarianDTO(AddVeterinarianFormDTO addVeterinarianFormDTO, String savedPath);
    VeterinarianDTO searchVeterinarian(int id);
    int getAllVetsCount();
}
