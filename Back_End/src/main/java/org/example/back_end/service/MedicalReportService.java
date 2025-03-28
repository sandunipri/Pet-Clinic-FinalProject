package org.example.back_end.service;

import org.example.back_end.dto.PetDTO;
import org.example.back_end.dto.VeterinarianDTO;
import org.example.back_end.entity.MedicalReport;

public interface MedicalReportService {
    void saveMedicalReport(MedicalReport medicalReport, PetDTO petDTO, VeterinarianDTO veterinarianDTO);

}
