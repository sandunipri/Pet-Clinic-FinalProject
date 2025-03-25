package org.example.back_end.dto;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.back_end.entity.Pet;
import org.example.back_end.entity.Veterinarian;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicalReportDTO {
    private int  reportId;
    private String report;
    private String date;
    private Pet pet;
    private Veterinarian veterinarian;
}
