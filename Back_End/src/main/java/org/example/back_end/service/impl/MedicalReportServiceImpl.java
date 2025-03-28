package org.example.back_end.service.impl;

import org.example.back_end.dto.PetDTO;
import org.example.back_end.dto.VeterinarianDTO;
import org.example.back_end.entity.MedicalReport;
import org.example.back_end.entity.Pet;
import org.example.back_end.entity.Veterinarian;
import org.example.back_end.repo.MedicalReportRepo;
import org.example.back_end.repo.PetRepo;
import org.example.back_end.repo.VeterinarianRepo;
import org.example.back_end.service.MedicalReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalReportServiceImpl implements MedicalReportService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MedicalReportRepo medicalReportRepo;

    @Autowired
    private PetRepo petRepo;

    @Autowired
    private VeterinarianRepo veterinarianRepo;

    @Override
    public void saveMedicalReport(MedicalReport medicalReport, PetDTO petDTO, VeterinarianDTO veterinarianDTO) {
        MedicalReport medicalReport1 = modelMapper.map(medicalReport, MedicalReport.class);
        Pet newpet = modelMapper.map(petDTO, Pet.class);
        Veterinarian veterinarian = modelMapper.map(veterinarianDTO, Veterinarian.class);

        medicalReport1.setPet(newpet);
        medicalReport1.setVeterinarian(veterinarian);

        medicalReportRepo.save(medicalReport1);
        System.out.println("Medical Report saved: " + medicalReport1);


    }
}
