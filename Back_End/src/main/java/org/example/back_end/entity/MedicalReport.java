package org.example.back_end.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity(name = "medicalReport")
public class MedicalReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportId;
    private String report;
    private String date;

    @ManyToOne
    private Pet pet;

    @ManyToOne
    private Veterinarian veterinarian;

}
