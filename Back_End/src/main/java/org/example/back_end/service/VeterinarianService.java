package org.example.back_end.service;

import org.example.back_end.dto.VeterinarianDTO;

import java.util.List;

public interface VeterinarianService {
    void saveVeterinarian(VeterinarianDTO veterinarian);

    List<VeterinarianDTO> getAllVeterinarian();

}
