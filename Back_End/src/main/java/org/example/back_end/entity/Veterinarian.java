package org.example.back_end.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Table(name = "vet")
@Entity
public class Veterinarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String specialty;
    private String profileImage;

    @OneToMany(mappedBy = "veterinarian")
    private List<MedicalReport> medicalReportList;

    @OneToMany(mappedBy = "veterinarian")
    private List<Appointments> appointmentsList;
}
